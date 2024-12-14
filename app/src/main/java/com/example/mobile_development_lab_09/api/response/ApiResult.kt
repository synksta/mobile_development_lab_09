package com.example.mobile_development_lab_09.api.response

import com.example.mobile_development_lab_09.model.Movie

sealed class ApiResult {
    data class Success(val movies: List<Movie?>) : ApiResult()
    data class Error(val message: String) : ApiResult()
}
