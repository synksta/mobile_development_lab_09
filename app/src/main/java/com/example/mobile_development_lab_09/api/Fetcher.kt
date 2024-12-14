package com.example.mobile_development_lab_09.api

import com.example.mobile_development_lab_09.api.repository.MovieRepository
import com.example.mobile_development_lab_09.api.response.MovieResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Fetcher(private val movieRepository: MovieRepository) {

    fun fetchMoviesList(searchQuery: String, year: String? = null, callback: (MovieResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.searchMovies(searchQuery, year)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }

    fun fetchMovie(title: String, year: String?, callback: (MovieResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.getMovie(title, year)
            withContext(Dispatchers.Main) {
                callback(result)
            }
        }
    }
}
