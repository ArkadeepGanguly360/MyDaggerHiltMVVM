package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ExpandableParentListItemBinding
import com.development.mydaggerhiltmvvm.interfaces.OnExpandableListItemClickListener
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListenerWithView
import com.development.mydaggerhiltmvvm.model.ExpandableChildData
import com.development.mydaggerhiltmvvm.model.ExpandableParentData

class ExpandableParentAdapter(
    val context: Context,
    val list: ArrayList<ExpandableParentData>,
    val listener: OnExpandableListItemClickListener
) :
    RecyclerView.Adapter<ExpandableParentAdapter.ViewHolder>() {
    var childList = ArrayList<ExpandableChildData>()
    private lateinit var expandableChildAdapter: ExpandableChildAdapter

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
        val rowBinding: ExpandableParentListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.expandable_parent_list_item,
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

        if(childList.isEmpty()) {
            getChildList()
        }

        holder.rowBinding.apply {
            name = item.parentName

            tvName.setOnClickListener {
                if (recycleChild.visibility == View.GONE) {

                    recycleChild.visibility = View.VISIBLE
                    imgArrow.background = ContextCompat.getDrawable(context, R.drawable.ic_up_arrow)

                    expandableChildAdapter = ExpandableChildAdapter(
                        context,
                        childList,
                        object : RecyclerViewItemOnClickListenerWithView {
                            override fun onIemClicked(view: View?, childPosition: Int?) {
                                listener.onIemClicked(view,position,childPosition)
                            }
                        })

                    recycleChild.apply {
                        adapter = expandableChildAdapter
                    }
                }
                else {
                    recycleChild.visibility = View.GONE
                    imgArrow.background = ContextCompat.getDrawable(context, R.drawable.ic_down_arrow)
                }
            }
        }
    }

    private fun getChildList() {
        childList.add(
            ExpandableChildData(
                "Dipanwita", "0"
            )
        )
        childList.add(
            ExpandableChildData(
                "Nilam", "1"
            )
        )
        childList.add(
            ExpandableChildData(
                "Tapasree", "2"
            )
        )
        childList.add(
            ExpandableChildData(
                "Poulami", "3"
            )
        )
    }

    inner class ViewHolder(val rowBinding: ExpandableParentListItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)

}
