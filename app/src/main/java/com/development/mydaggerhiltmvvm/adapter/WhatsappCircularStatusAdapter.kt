package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.WhatsappCircularStatusAdapterItemBinding
import com.development.mydaggerhiltmvvm.interfaces.CircularStatusViewOnClickListener
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListenerWithView
import com.development.mydaggerhiltmvvm.model.StaggeredGridData
import com.development.mydaggerhiltmvvm.model.WhatsappStatus
import com.development.mydaggerhiltmvvm.model.WhatsappUser
import com.development.mydaggerhiltmvvm.util.whatsappCircularStatus.CircularStatusView


class WhatsappCircularStatusAdapter(
    val context: Context,
    val list: List<WhatsappUser>,
    val listener: CircularStatusViewOnClickListener
) :
    RecyclerView.Adapter<WhatsappCircularStatusAdapter.ViewHolder>() {

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
        val rowBinding: WhatsappCircularStatusAdapterItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.whatsapp_circular_status_adapter_item,
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

            tvName.text = item.userName
            imgUser.setImageDrawable(item.userImage)

            val statusList: List<WhatsappStatus> = item.statusList
            circularStatusView.setPortionsCount(statusList.size)
            val notSeenColor: Int = circularStatusView.context.resources.getColor(R.color.blue)
            val seenColor = Color.GRAY

            if (item.allSeen) {
                //set all portions color
                circularStatusView.setPortionsColor(seenColor)
            } else {
                for (i in statusList.indices) {
                    val status: WhatsappStatus = statusList[i]
                    val color = if (status.isSeen) seenColor else notSeenColor
                    //set specific color for every portion
                    circularStatusView.setPortionColorForIndex(i, color)
                }
            }

            itemView.setOnClickListener {
                listener.onIemClicked(circularStatusView,position)
            }
        }
    }

    inner class ViewHolder(val rowBinding: WhatsappCircularStatusAdapterItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
