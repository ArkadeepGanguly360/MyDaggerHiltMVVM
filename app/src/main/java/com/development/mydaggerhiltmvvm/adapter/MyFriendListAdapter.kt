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
import com.development.mydaggerhiltmvvm.databinding.FriendListItemBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.util.MyConstant

class MyFriendListAdapter(
    val context: Context,
    val list: ArrayList<FriendsData>,
    val listener: RecyclerViewItemOnClickListener
) :
    RecyclerView.Adapter<MyFriendListAdapter.ViewHolder>(/*DiffUtilNote()*/) {

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
        val rowBinding: FriendListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.friend_list_item,
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
            name = item.friendDetails.full_name

            if(item.friendDetails.location.contains(",")){
                var location =  StringBuilder(item.friendDetails.location).insert(item.friendDetails.location.indexOf(',')+1, " ").toString()
                location = location
            }else {
                location = item.friendDetails.location
            }

            image = MyConstant.COMMON_CONST.PROFILE_IMG_PATH + item.friendDetails.profile_image

            if (position % 2 == 0) {
                cons.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
            } else {
                cons.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }

            cons.setOnClickListener {
                listener.onViewClick(position)
            }
        }
    }

    inner class ViewHolder(val rowBinding: FriendListItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)

   /* class DiffUtilNote : DiffUtil.ItemCallback<FriendsData>() {
        override fun areItemsTheSame(oldItem: FriendsData, newItem: FriendsData): Boolean {
            return newItem._id == oldItem._id
        }

        override fun areContentsTheSame(oldItem: FriendsData, newItem: FriendsData): Boolean {
            return newItem == oldItem
        }
    }*/
}
