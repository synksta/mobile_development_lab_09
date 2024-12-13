package com.example.mobile_development_lab_09.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_development_lab_09.model.Movie
import com.example.mobile_development_lab_09.databinding.ItemMoviesToWatchBinding
import com.example.mobile_development_lab_09.extenstion.fetchImage

class MoviesToWatchAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesToWatchAdapter.MovieViewHolder>() {

    private val selectedMovies = mutableSetOf<Movie>()

    class MovieViewHolder(private val binding: ItemMoviesToWatchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, isSelected: Boolean, onSelectChangeListener: (Boolean) -> Unit) {
            binding.textViewTitle.text = movie.title
            binding.textViewYear.text = movie.year
            binding.imageViewPoster.fetchImage(movie.poster)
            binding.checkboxSelect.isChecked = isSelected

            binding.checkboxSelect.setOnCheckedChangeListener { _, isChecked ->
                onSelectChangeListener(isChecked)
            }

            itemView.setOnClickListener {
                binding.checkboxSelect.isChecked = !binding.checkboxSelect.isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMoviesToWatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(moviesList[position], selectedMovies.contains(moviesList[position])) { isSelected ->
            if (isSelected) {
                selectedMovies.add(moviesList[position])
            } else {
                selectedMovies.remove(moviesList[position])
            }
        }
    }

    override fun getItemCount(): Int = moviesList.size

    fun getSelectedMovies(): List<Movie> {
        return selectedMovies.toList()
    }
}
