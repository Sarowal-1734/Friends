package com.example.friends.api

import com.example.friends.models.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("/api")
    suspend fun getUsers(
        @Query("page")
        page: Int?,
        @Query("results")
        results: Int?
    ): Response<UserResponse>
}