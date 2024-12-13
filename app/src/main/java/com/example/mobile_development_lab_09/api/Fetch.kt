package com.example.mobile_development_lab_09.api

import android.widget.Toast
import com.example.mobile_development_lab_09.api.ApiClient.apiService
import com.example.mobile_development_lab_09.api.repository.MovieRepository
import com.example.mobile_development_lab_09.api.response.MovieResult
import com.example.mobile_development_lab_09.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Fetch(private val movieRepository: MovieRepository) {

    private fun updateMovieList(movies: List<Movie>) {
        // Логика для обновления вашего RecyclerView или другого UI элемента с новыми данными
    }

    fun fetchMovies(searchQuery: String, year: String? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.searchMovies(searchQuery, year)
            withContext(Dispatchers.Main) {
                when (result) {
                    is MovieResult.Success -> {
                        // Обновите UI с полученными фильмами
                        updateMovieList(result.movies.map { it.toMovie() })
                    }
                    is MovieResult.Error -> {
                        // Показать сообщение об ошибке
                        showError(result.message)
                    }
                }
            }
        }
    }

    fun fetchMovieDetails(title: String, year: String? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.getMovie(title, year)
            withContext(Dispatchers.Main) {
                when (result) {
                    is MovieResult.Success -> {
                        // Обновите UI с деталями фильма
                        updateMovieDetails(result.movies.first().toMovie())
                    }
                    is MovieResult.Error -> {
                        // Показать сообщение об ошибке
                        showError(result.message)
                    }
                }
            }
        }
    }

    private fun updateMovieDetails(movie: Movie) {
        // Логика для обновления UI с деталями фильма (например, заголовок, постер и т.д.)
    }

    private fun showError(message: String) {
        // Логика для отображения сообщения об ошибке (например, через Toast или Snackbar)
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}



