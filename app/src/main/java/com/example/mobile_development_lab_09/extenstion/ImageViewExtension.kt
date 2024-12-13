// ImageViewExtensions.kt
package com.example.mobile_development_lab_09.extenstion

import android.widget.ImageView
import com.example.mobile_development_lab_09.R
import com.squareup.picasso.Picasso

fun ImageView.fetchImage(url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.image_placeholder_error)
        .into(this)
}






//
//мне нужно создать активити - MoviesToWatch.
//
//мне нужна верстка и код исполненный в MVVM. у меня есть модель - Movie
//
//package com.example.mobile_development_lab_09.model
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "movies")
//data class Movie(
//    @PrimaryKey val imdbId: String,
//    val title: String,
//    val year: String,
//    val type: String,
//    val poster: String
//)
//
//Активити должна представлять собой recyclerview. когда этот ресайклер пуст, отображается большая иконка R.drawable.empty_movie и под ней надпись "There are currently no movies in your watch list. Tap the button below to get started!"
//
//сверху присутствует actionbar на котором есть кнопка с иконкой R.drawable.trash. пока что не будем давать ей применение.
//
//также присутствует FAB c иконкой R.drawable.add.
//
//recyclerview в непустом состоянии должен отображать элементы списка, которые содержат иконку постера фильма, его название, год выхода и флажок для выбора на удаление. (стоит это оформить как отдельный layout)