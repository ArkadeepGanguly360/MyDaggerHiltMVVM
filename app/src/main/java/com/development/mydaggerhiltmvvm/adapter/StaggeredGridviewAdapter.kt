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
import com.development.mydaggerhiltmvvm.databinding.StaggeredGridAdapterItemBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.AutoImageSliderData
import com.development.mydaggerhiltmvvm.model.StaggeredGridData


class StaggeredGridviewAdapter(
    val context: Context,
    val list: List<StaggeredGridData>,
    val listener: RecyclerViewItemOnClickListener
) :
    RecyclerView.Adapter<StaggeredGridviewAdapter.ViewHolder>() {

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
        val rowBinding: StaggeredGridAdapterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.staggered_grid_adapter_item,
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
            img.setImageResource(item.image)
        }
    }

    inner class ViewHolder(val rowBinding: StaggeredGridAdapterItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
