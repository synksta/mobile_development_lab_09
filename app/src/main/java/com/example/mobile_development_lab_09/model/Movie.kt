package com.example.mobile_development_lab_09.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val imdbId: String,
    val title: String,
    val year: String,
    val type: String,
    val poster: String
)