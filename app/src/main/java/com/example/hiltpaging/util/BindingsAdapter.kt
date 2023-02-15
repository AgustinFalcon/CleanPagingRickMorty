package com.example.hiltpaging.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.hiltpaging.R
import com.google.android.material.textview.MaterialTextView
import com.example.hiltpaging.data.model.Status


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty()) return
    Glide.with(this).load(url).into(this)
}


@BindingAdapter("status")
fun MaterialTextView.status(status: Status) {
    text = status.toString()
    when (status) {
        Status.ALIVE -> setDrawableLeft(R.color.green)
        Status.DEAD -> setDrawableLeft(R.color.red)
        Status.UNKNOWN -> setDrawableLeft(R.color.gray)
    }
}