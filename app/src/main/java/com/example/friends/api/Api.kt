package com.example.friends.api

import com.example.friends.models.User
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/api")
    suspend fun getUsers( @Query("results") results: Int? ): User
}