package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ExpandableChildListItemBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListenerWithView
import com.development.mydaggerhiltmvvm.model.ExpandableChildData

class ExpandableChildAdapter(
    val context: Context,
    val list: ArrayList<ExpandableChildData>,
    val listener: RecyclerViewItemOnClickListenerWithView
) :
    RecyclerView.Adapter<ExpandableChildAdapter.ViewHolder>() {

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
        val rowBinding: ExpandableChildListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.expandable_child_list_item,
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
            name = item.childName

            tvChildname.setOnClickListener {
                listener.onIemClicked(it,position)
            }
        }
    }

    inner class ViewHolder(val rowBinding: ExpandableChildListItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)

}
