package com.example.mobile_development_lab_09.api

import com.example.mobile_development_lab_09.api.response.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") search: String,
        @Query("y") year: String? = null
    ): ApiResponse

    @GET("/")
    suspend fun getMovie(
        @Query("t") search: String,
        @Query("y") year: String? = null
    ): ApiResponse
}