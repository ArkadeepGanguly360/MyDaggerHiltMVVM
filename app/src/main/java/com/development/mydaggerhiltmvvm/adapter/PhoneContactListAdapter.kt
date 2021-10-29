package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ContactListItemBinding
import com.development.mydaggerhiltmvvm.interfaces.OnCheckedAutoCompleteItemClicked
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.PhoneContactModel
import com.development.mydaggerhiltmvvm.util.MyConstant

class PhoneContactListAdapter(
    val context: Context,
    val list: ArrayList<PhoneContactModel>,
    val listener: OnCheckedAutoCompleteItemClicked
) :
    RecyclerView.Adapter<PhoneContactListAdapter.ViewHolder>() {
    private val sparseArray = SparseBooleanArray()

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
        val rowBinding: ContactListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.contact_list_item,
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
            name = item.name
            phno = item.phNo
            image = item.photo

            imgTick.setOnClickListener{
                if(imgTick.visibility == View.GONE){
                    isVisible = true
                    setChecked(position)
                }
                else{
                    isVisible = false
                    listener.onIemClicked(position,false)
                }
            }

            img.setOnClickListener {
                imgTick.performClick()
            }

         /*   //Todo For CheckBox
            holder.rowBinding.isChecked = sparseArray.get(position)

            holder.rowBinding.check.setOnClickListener {
                setChecked(position)
            }*/
        }
    }

    private fun setChecked(position: Int){
        if(sparseArray.get(position)){
            sparseArray.delete(position)
        }
        else
            sparseArray.put(position,true)

        notifyDataSetChanged()
        listener.onIemClicked(position,true)
    }

   /* //Todo For CheckBox
    fun setChecked(position: Int){
        if(sparseArray.get(position)){
            sparseArray.delete(position)
        }
        else
            sparseArray.put(position,true)

        notifyDataSetChanged()
        listener.onViewClick(position)
    }*/

    inner class ViewHolder(val rowBinding: ContactListItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
