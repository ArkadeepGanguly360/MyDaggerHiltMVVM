package com.development.mydaggerhiltmvvm.util.bindingUtil

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class ImageViewBindingAdapter {
    companion object {
        @BindingAdapter("bind:imageUrl", "bind:placeHolder")
        @JvmStatic
        fun loadImage(view: AppCompatImageView, imageUrl: String?, @DrawableRes placeholder: Int) {
            var url = imageUrl
            if (url.isNullOrEmpty())
                url =
                    "image"       // picasso cant load empty string, in case if the image url is empty then set it to the default value to avoid ANR

            val picasso = Picasso.get()
            picasso.load(url).error(placeholder)
                .placeholder(placeholder)
//                .fit()
                .into(view)

        }
    }
}