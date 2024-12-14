package com.example.mobile_development_lab_09

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_development_lab_09.api.ApiClient.apiService
import com.example.mobile_development_lab_09.api.Fetcher
import com.example.mobile_development_lab_09.api.repository.MovieRepository
import com.example.mobile_development_lab_09.api.response.MovieResponse
import com.example.mobile_development_lab_09.api.response.MovieResult
import com.example.mobile_development_lab_09.databinding.ActivityAddMovieBinding
import com.example.mobile_development_lab_09.db.MovieDatabase
import com.example.mobile_development_lab_09.extenstion.fetchImage
import com.example.mobile_development_lab_09.model.Movie
import com.example.mobile_development_lab_09.viewmodel.MovieViewModel
import com.example.mobile_development_lab_09.viewmodel.MovieViewModelFactory
import java.util.Calendar

class AddMovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddMovieBinding
    private lateinit var fetcher: Fetcher

    private var isUserInput: Boolean = false

    // Переменная movie с пользовательским сеттером
    private var _movie: Movie? = null
    var movie: Movie?
        get() = _movie
        set(value) {
            _movie = value
            updateButtonState() // Обновляем состояние кнопки при изменении movie
            if (value==null) binding.poster.setImageDrawable(null)
        }

    // ViewModel для работы с базой данных
    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModelFactory(MovieDatabase.getDatabase(application).movieDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityAddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация Fetcher
        val movieRepository = MovieRepository(apiService)
        fetcher = Fetcher(movieRepository)

        // Настройка Spinner для выбора года
        setupYearSpinner()

        // Обработка нажатия на кнопку Browse для поиска фильмов
        binding.buttonSearch.setOnClickListener {
//            val intent = Intent(this, MoviesBrowseActivity::class.java)
            Toast.makeText(this, "Browse clicked", Toast.LENGTH_SHORT).show()

        }

        // Устанавливаем слушатель для обработки пользовательского ввода
        binding.editTextTitle.setOnFocusChangeListener { _, hasFocus ->
            isUserInput = hasFocus // Устанавливаем флаг в true, когда поле ввода в фокусе
        }

        binding.spinnerYear.setOnFocusChangeListener { _, hasFocus ->
            isUserInput = hasFocus // Устанавливаем флаг в true, когда поле ввода в фокусе
        }

        // Сброс переменной movie при изменении текста в полях ввода.
        binding.editTextTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isUserInput){
                    movie = null // Сбрасываем movie при изменении названия.
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (isUserInput) {
                    movie = null // Сбрасываем movie при изменении года.
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupYearSpinner() {
        val years = getYearsList() // Получаем список годов с добавленным значением "Any"
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerYear.adapter = adapter
        setYearSpinnerValue(Calendar.getInstance().get(Calendar.YEAR).toString())
    }

    private fun getYearsList(): List<String> {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR) + 5
        return listOf("Any") + (1870..currentYear).map { it.toString() }.reversed() // Возвращаем список годов
    }

    private fun getYearIndex(year: String): Int {

        val years = getYearsList() // Получаем список годов

        return years.indexOf(year) // Ищем индекс года в списке.
    }

    private fun setYearSpinnerValue(year: String){
        val yearIndex = getYearIndex(year)
        if (yearIndex >= 0) {
            binding.spinnerYear.setSelection(yearIndex) // Устанавливаем выбор в спиннере
        } else {
            showError("Year not found in the list") // Обработка случая, если год не найден
        }
    }

    private fun fetchMovieDetails() {
        binding.editTextTitle.clearFocus()
        binding.spinnerYear.clearFocus()
        val title: String = binding.editTextTitle.text.toString()
        val selectedYear = binding.spinnerYear.selectedItem.toString()
        val year: String? = if (selectedYear == "Any") null else selectedYear

        binding.progressBar.visibility = View.VISIBLE
        fetcher.fetchMovie(title, year) { result ->
            when (result) {
                is MovieResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (result.movies.isNotEmpty()) {

                        movie = result.movies.first() // Присваиваем значение переменной movie

                        val fetchedTitle: String = movie!!.title
                        val fetchedYear: String = movie!!.year
                        val fetchedPoster: String = movie!!.poster

                        // Заполняем поля данными из ответа API
                        binding.editTextTitle.setText(fetchedTitle)
                        // Убедитесь, что year - это строка и соответствует элементам спиннера
                        setYearSpinnerValue(fetchedYear)
                        binding.poster.fetchImage(fetchedPoster)

                        // Обновляем состояние кнопки после получения данных
                        updateButtonState()
                    } else {
                        showError("No movies found") // Обработка случая, если список фильмов пуст
                    }
                }
                is MovieResult.Error -> {
                    Log.e("GMD", result.message)
                    showError(result.message) // Показать сообщение об ошибке через Toast
                }
            }
        }
    }




    private fun addMovieToDatabase() {
        if (movie != null){
            movieViewModel.insert(movie!!) // Добавляем фильм в базу данных.
            Toast.makeText(this, "Movie ${movie!!.title} added successfully!", Toast.LENGTH_SHORT).show()
            finish() // Закрываем активити и возвращаемся к MoviesToWatchActivity.
        }
    }

    private fun updateButtonState() {
        Log.i("GMD","update button state")
        if (movie == null) {
            binding.buttonAction.text = getString(R.string.search_movie) // Меняем текст кнопки на SEARCH MOVIE.
            binding.buttonAction.setOnClickListener {
                fetchMovieDetails() // Если movie пусто, выполняем поиск фильма.
            }
        } else {
            binding.buttonAction.text = getString(R.string.add_movie) // Меняем текст кнопки на ADD MOVIE.
            binding.buttonAction.setOnClickListener {
                addMovieToDatabase() // Если movie не пусто, добавляем фильм в базу данных.
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
