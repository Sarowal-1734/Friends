package com.example.friends.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.friends.UserApplication
import com.example.friends.models.UserResponse
import com.example.friends.repository.UserRepository
import com.example.friends.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.friends.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class UserViewModel(
    val app: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(app) {

    val users: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    var usersPageNumber = 1
    var usersPageResponse: UserResponse? = null

    // Initially load 10 user
    init {
        getUsers()
    }

    // get users
    fun getUsers() =
        viewModelScope.launch {
            safeGetUsersCall()
        }

    // get users
    private suspend fun safeGetUsersCall() {
        if (usersPageNumber == 1) {
            users.postValue(Resource.Loading())
        } else {
            users.postValue(Resource.Paginating())
        }
        try {
            if (hasInternetConnection()) {
                // get user response
                val response =
                    userRepository.getUsers(
                        usersPageNumber,
                        QUERY_PAGE_SIZE
                    )
                // handle the response
                users.postValue(handleResponse(response))
            } else {
                users.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> users.postValue(Resource.Error("Network Failure"))
                else -> users.postValue(Resource.Error("Conversion error"))
            }
        }
    }

    private fun handleResponse(response: Response<UserResponse>): Resource<UserResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                // Pagination
                usersPageNumber++
                if (usersPageResponse == null) {
                    usersPageResponse = resultResponse
                } else {
                    val oldRepo = usersPageResponse?.results
                    val newRepo = resultResponse.results
                    oldRepo?.addAll(newRepo)
                }
                return Resource.Success(usersPageResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // Check internet connection
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<UserApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> return true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}