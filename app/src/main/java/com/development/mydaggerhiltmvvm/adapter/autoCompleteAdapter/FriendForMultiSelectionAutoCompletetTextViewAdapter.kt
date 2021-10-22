package com.development.mydaggerhiltmvvm.adapter.autoCompleteAdapter

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.interfaces.OnCheckedAutoCompleteItemClicked
import com.development.mydaggerhiltmvvm.model.MyFriendSData
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView

class FriendForMultiSelectionAutoCompletetTextViewAdapter(
    context: Context,
    var resource: Int,
    val list: ArrayList<MyFriendSData>,
    val listener: OnCheckedAutoCompleteItemClicked
) :
    ArrayAdapter<MyFriendSData>(context, resource, list) {

    private val sparseBooleanArray = SparseBooleanArray()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(parent.context)
            view = inflater.inflate(resource, null)
        }
        val data: MyFriendSData? = getItem(position)
        val textView: MaterialTextView = view!!.findViewById(R.id.tv_auto)
        val check_value: MaterialCheckBox = view!!.findViewById(R.id.check_value)

        assert(data != null)
        textView.text = data?.name
        check_value.setOnClickListener { v: View? ->
            setSparseValue(
                position
            )
        }
        check_value.isChecked = sparseBooleanArray[position]

        return view
    }

    private fun setSparseValue(position: Int) {
        if (sparseBooleanArray.size() == 0)
            sparseBooleanArray.put(
                position,
                true
            )
        else if (sparseBooleanArray.get(position))
            sparseBooleanArray.delete(position)
        else
            sparseBooleanArray.put(
                position,
                true
            )

        notifyDataSetChanged()
        listener.onIemClicked(position, sparseBooleanArray?.get(position))
    }

    override fun getCount(): Int {
        return if (list.isEmpty())
            0
        else
            list.size
    }

    override fun getItem(position: Int): MyFriendSData? {
        return list[position]
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            results?.values = list
            results?.count = count
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

        }
    }
}