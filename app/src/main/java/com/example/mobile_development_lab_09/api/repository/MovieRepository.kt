package com.example.mobile_development_lab_09.api.repository

import android.util.Log
import com.example.mobile_development_lab_09.api.ApiService
import com.example.mobile_development_lab_09.api.response.MovieResult
import com.example.mobile_development_lab_09.model.Movie

class MovieRepository(private val apiService: ApiService) {

    suspend fun searchMovies(searchQuery: String, year: String? = null): MovieResult {
        return try {
            val response = apiService.searchMovies(searchQuery, year)

            // Проверка успешного ответа
            if (response.response == "True") {
                val movies = response.movies?.mapNotNull {
                    if (it.isMovie()) it.toMovie() else null
                } ?: emptyList() // Если movies == null, возвращаем пустой список

                MovieResult.Success(movies)
            } else {
                // Ошибка от API
                MovieResult.Error(response.error ?: "Unknown API error")
            }
        } catch (e: Exception) {
            // Исключение при выполнении запроса
            MovieResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getMovie(title: String, year: String? = null): MovieResult {
        return try {
            val response = apiService.getMovie(title, year)

            // Проверка успешного ответа
            if (response.response == "True") {
                val movies = listOf(response.toMovie())
                MovieResult.Success(movies)
            } else {
                // Ошибка от API
                MovieResult.Error(response.error ?: "Unknown API error")
            }
        } catch (e: Exception) {
            // Исключение при выполнении запроса
            MovieResult.Error(e.message ?: "Unknown error")
        }
    }
}
