package com.inverse.photoeditor.hepers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.inverse.core.Log
import com.inverse.photoeditor.R

object BindingHelper{
    @JvmStatic
    @BindingAdapter("setImageUsingGlide")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context).load(url).placeholder(R.drawable.loading).into(view)
        }
    }
}
