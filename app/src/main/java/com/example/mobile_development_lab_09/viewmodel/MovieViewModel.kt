package com.example.mobile_development_lab_09.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_development_lab_09.model.Movie
import com.example.mobile_development_lab_09.db.MovieDao
import kotlinx.coroutines.launch

class MovieViewModel(private val movieDao: MovieDao) : ViewModel() {

    val allMovies: LiveData<List<Movie>> = movieDao.getAllMovies()

    fun insert(movie: Movie) {
        viewModelScope.launch {
            movieDao.insert(movie)
        }
    }

    fun deleteMovies(movies: List<Movie>) {
        viewModelScope.launch {
            movieDao.deleteMovies(movies)
        }
    }
}
