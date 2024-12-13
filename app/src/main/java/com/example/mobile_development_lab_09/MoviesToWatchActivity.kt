package com.example.mobile_development_lab_09

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_development_lab_09.db.MovieDatabase
import com.example.mobile_development_lab_09.viewmodel.MovieViewModel
import com.example.mobile_development_lab_09.viewmodel.MovieViewModelFactory
import com.example.mobile_development_lab_09.databinding.ActivityMoviesToWatchBinding
import com.example.mobile_development_lab_09.ui.MoviesToWatchAdapter

class MoviesToWatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesToWatchBinding

    // Используем делегат by viewModels для получения экземпляра MovieViewModel
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieDatabase.getDatabase(application).movieDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityMoviesToWatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настройка RecyclerView.
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Наблюдение за изменениями в списке фильмов.
        movieViewModel.allMovies.observe(this, Observer { movies ->
            if (movies.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE

                binding.recyclerView.adapter = MoviesToWatchAdapter(movies)
            }
        })

        // Обработка нажатия на FAB для добавления нового фильма.
        binding.fabAddMovie.setOnClickListener {
            // Здесь можно открыть диалог или новую активити для добавления фильма.
            // Например:
            // openAddMovieDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movies_to_watch, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteSelectedMovies()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteSelectedMovies() {
        val selectedMovies = (binding.recyclerView.adapter as? MoviesToWatchAdapter)?.getSelectedMovies()
        if (!selectedMovies.isNullOrEmpty()) {
            movieViewModel.deleteMovies(selectedMovies)
            // Обновите список фильмов после удаления, если это необходимо.
            // Например, можно вызвать метод обновления списка или перезагрузить данные.
        }
    }
}
