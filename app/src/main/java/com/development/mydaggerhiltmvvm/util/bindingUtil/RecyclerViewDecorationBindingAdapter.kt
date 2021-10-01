package com.development.mydaggerhiltmvvm.util.bindingUtil

import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewDecorationBindingAdapter {
    companion object {
        @BindingAdapter("bind:itemDecoration")
        @JvmStatic
        fun addDecoration(view: RecyclerView, decoration: RecyclerView.ItemDecoration) {
            view.addItemDecoration(decoration)
        }
    }
}