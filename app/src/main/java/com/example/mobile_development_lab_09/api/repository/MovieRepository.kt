package com.example.mobile_development_lab_09.api.repository

import com.example.mobile_development_lab_09.api.ApiService
import com.example.mobile_development_lab_09.api.response.MovieResult

class MovieRepository(private val apiService: ApiService) {

    suspend fun searchMovies(searchQuery: String, year: String? = null): MovieResult {
        return try {
            val response = apiService.searchMovies(searchQuery, year)
            if (response.response == "True") {
                // Успешный ответ
                MovieResult.Success(response.movies)
            } else {
                // Ошибка от API
                MovieResult.Error(response.totalResults) // Или фиксированное сообщение
            }
        } catch (e: Exception) {
            // Исключение при выполнении запроса
            MovieResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun getMovie(title: String, year: String? = null): MovieResult {
        return try {
            val response = apiService.getMovie(title, year)
            if (response.imdbId.isNotEmpty()) {
                // Успешный ответ
                MovieResult.Success(listOf(response)) // Возвращаем фильм как список
            } else {
                // Ошибка от API
                MovieResult.Error("Movie not found!")
            }
        } catch (e: Exception) {
            // Исключение при выполнении запроса
            MovieResult.Error(e.message ?: "Unknown error")
        }
    }
}
