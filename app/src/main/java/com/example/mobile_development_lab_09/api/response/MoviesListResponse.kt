package com.example.mobile_development_lab_09.api.response

import com.google.gson.annotations.SerializedName

data class MoviesListResponse (
    @SerializedName("Search") val movies: List<MovieResponse>,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: String
)