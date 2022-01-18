package com.example.friends.repository

import com.example.friends.api.RetrofitInstance

class UserRepository {
    suspend fun getUsers(page: Int, results: Int) = RetrofitInstance.api.getUsers(page, results)
}