package com.example.mobile_development_lab_09.api

import com.example.mobile_development_lab_09.api.repository.MovieRepository
import com.example.mobile_development_lab_09.api.response.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApiFetcher(private val movieRepository: MovieRepository) {

    fun fetchMoviesList(searchQuery: String, year: String? = null, callback: (ApiResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.searchMovies(searchQuery, year)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun fetchMovie(title: String, year: String?, callback: (ApiResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.getMovie(title, year)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }
}