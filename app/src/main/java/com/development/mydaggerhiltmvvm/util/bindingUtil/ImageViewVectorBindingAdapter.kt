package com.development.mydaggerhiltmvvm.util.bindingUtil

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter


class ImageViewVectorBindingAdapter {
    companion object {
        @BindingAdapter("app:srcVector")
        @JvmStatic
        fun setSrcVector(view: AppCompatImageView, @DrawableRes drawable: Int) {
            view.setImageResource(drawable)
        }
    }
}