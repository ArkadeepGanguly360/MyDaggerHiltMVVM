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
import com.development.mydaggerhiltmvvm.databinding.InstagramSuggestionItemBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.AutoImageSliderData


class InstagramSuggestionViewPagerAdapter(
    val context: Context,
    val list: ArrayList<AutoImageSliderData>,
    val listener: RecyclerViewItemOnClickListener
) :
    RecyclerView.Adapter<InstagramSuggestionViewPagerAdapter.ViewHolder>() {

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
        val rowBinding: InstagramSuggestionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.instagram_suggestion_item,
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

            cons.setOnClickListener{
                listener.onViewClick(position)
            }
        }
    }

    inner class ViewHolder(val rowBinding: InstagramSuggestionItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
