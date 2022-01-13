package com.example.friends.ui.fragments.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.friends.api.RetrofitInstance
import com.example.friends.models.User
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class UserViewModel: ViewModel() {
    // For debugging
    private val TAG = "UserViewModel"
    // Show/Hide progressBar
    private val isLoading = MutableLiveData<Boolean>()
    val user = MutableLiveData<User>()

    // Initially load 10 user
    init {
        getUsers()
    }

    // Get 10 user list from api
    private fun getUsers() {
        // Show progressBar
        isLoading.value = true
        viewModelScope.launch {
            try {
                // Get 10 users
                user.value = RetrofitInstance.api.getUsers(10)
                // Hide progressBar
                isLoading.value = false
            } catch (e: IOException) {
                Log.e(TAG, "You might not have internet connection")
                // Hide progressBar
                isLoading.value = false
                return@launch
            } catch (e: HttpException) {
                Log.e(TAG, "Unexpected response")
                // Hide progressBar
                isLoading.value = false
                return@launch
            }
            // Hide progressBar
            isLoading.value = false
        }
    }

    // Show/Hide progressBar
    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

}