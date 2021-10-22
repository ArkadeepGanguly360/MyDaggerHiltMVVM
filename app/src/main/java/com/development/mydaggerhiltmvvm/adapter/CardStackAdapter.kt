package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.CardAdapterItemBinding
import com.development.mydaggerhiltmvvm.model.CardData


class CardStackAdapter(
    val context: Context,
    val list: ArrayList<CardData/*ItemModel*/>
    //val listener: RecyclerViewItemOnClickListener
) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    var lastPosition = 0
    private val sparseArray = SparseBooleanArray()

    init {
        sparseArray.put(0, true)
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rowBinding: CardAdapterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_adapter_item,
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

        holder.rowBinding.img.setImageDrawable(item.img)
        holder.rowBinding.itemAge.text = item.age
        holder.rowBinding.itemCity.text = item.city
        holder.rowBinding.itemName.text = item.name
}

inner class ViewHolder(val rowBinding: CardAdapterItemBinding) :
    RecyclerView.ViewHolder(rowBinding.root)
}
