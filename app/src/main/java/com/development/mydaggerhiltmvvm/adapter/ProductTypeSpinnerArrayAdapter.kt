package com.development.mydaggerhiltmvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.model.ProductCategory
import kotlinx.android.synthetic.main.spinner_product_type_item.view.*

class ProductTypeSpinnerArrayAdapter(context: Context, productTypeList: ArrayList<ProductCategory>) : ArrayAdapter<ProductCategory>(context, 0, productTypeList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val item = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_product_type_item, parent, false)
        view.tv_product_type.text = item!!.name

        return view
    }
}