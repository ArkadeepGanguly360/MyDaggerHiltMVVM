package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.AutoImageSliderItemBinding
import com.development.mydaggerhiltmvvm.model.AutoImageSliderData


class AutoImageSliderViewPagerAdapter(
    val context: Context,
    val list: ArrayList<AutoImageSliderData>
) :
    RecyclerView.Adapter<AutoImageSliderViewPagerAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rowBinding: AutoImageSliderItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.auto_image_slider_item,
            parent,
            false
        )
        return ViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.rowBinding.apply {
            image = item.image
            title = item.title
        }
    }

    inner class ViewHolder(val rowBinding: AutoImageSliderItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
