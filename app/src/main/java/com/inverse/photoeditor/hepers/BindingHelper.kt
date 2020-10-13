package com.inverse.photoeditor.hepers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingHelper{
    @JvmStatic
    @BindingAdapter("setImageUsingGlide")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context).load(url).into(view)
        }
    }
}
