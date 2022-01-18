package com.example.githubrepo.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.friends.repository.UserRepository
import com.example.friends.ui.UserViewModel

class UserViewModelProviderFactory(
    val app: Application,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(app, userRepository) as T
    }
}