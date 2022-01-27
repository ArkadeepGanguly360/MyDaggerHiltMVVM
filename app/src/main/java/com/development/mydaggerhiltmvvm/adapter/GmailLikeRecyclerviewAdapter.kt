package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.GmailAdapterItemBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.GmailData

class GmailLikeRecyclerviewAdapter(
    val context: Context,
    val list: ArrayList<GmailData>,
    val listener: RecyclerViewItemOnClickListener
) :
    RecyclerView.Adapter<GmailLikeRecyclerviewAdapter.ViewHolder>() {

    val selectedItems = SparseBooleanArray()
    private var currentSelectedPos = 0

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
        val rowBinding: GmailAdapterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.gmail_adapter_item,
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

        }
    }

    inner class ViewHolder(val rowBinding: GmailAdapterItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)

    fun getEmails(): List<GmailData?>? {
        return list
    }

    fun deleteEmails() {
        val emails: MutableList<GmailData> = java.util.ArrayList<GmailData>()
        for (email in emails) {
            if (email.isSelected) emails.add(email)
        }
        emails.removeAll(emails)
        notifyDataSetChanged()
        currentSelectedPos = -1
    }

    fun toggleSelection(position: Int) {
        currentSelectedPos = position
        if (selectedItems.get(position)) {
            selectedItems.delete(position)
            list.get(position).isSelected = false
        } else {
            selectedItems.put(position, true)
            list.get(position).isSelected = true
        }
        notifyItemChanged(position)
    }
}
