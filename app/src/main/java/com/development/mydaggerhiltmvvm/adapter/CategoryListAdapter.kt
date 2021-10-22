package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.databinding.AdapterCategoryListBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.CategoryData

class CategoryListAdapter(
    val context: Context,
    val list: ArrayList<CategoryData>?,
    val listener: RecyclerViewItemOnClickListener
    ) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(AdapterCategoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind()
    }


inner class ViewHolder(val rowBinding: AdapterCategoryListBinding) :
    RecyclerView.ViewHolder(rowBinding.root){
        fun onBind(){
            val item = list?.get(adapterPosition)
            rowBinding.tvCategoryName.text = item?.name

            itemView.setOnClickListener {
                listener.onViewClick(adapterPosition)
            }
        }
    }
}
