package com.example.mobile_development_lab_09.api.response

import com.example.mobile_development_lab_09.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("imdbID") val imdbId: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String
){
    fun toMovie(): Movie {
        return Movie(
            imdbId = this.imdbId,
            title = this.title,
            year = this.year,
            type = this.type,
            poster = this.poster
        )
    }
}
