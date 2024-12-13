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






