package com.example.mobile_development_lab_09.api.response

import com.example.mobile_development_lab_09.model.Movie

sealed class MovieResult {
    data class Success(val movies: List<Movie?>) : MovieResult()
    data class Error(val message: String) : MovieResult()
}
