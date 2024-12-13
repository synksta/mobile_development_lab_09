package com.example.mobile_development_lab_09.api.response

sealed class MovieResult {
    data class Success(val movies: List<MovieResponse>) : MovieResult()
    data class Error(val message: String) : MovieResult()
}
