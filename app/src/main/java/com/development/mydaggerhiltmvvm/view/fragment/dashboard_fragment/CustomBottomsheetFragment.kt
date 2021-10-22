package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.CategoryListAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomBottomsheetBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.CategoryData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.ArrayList

class CustomBottomsheetFragment : BaseFragment() {

    private lateinit var binding: FragmentCustomBottomsheetBinding
    var categoryName =  ObservableField("")
    private var categoryList = ArrayList<CategoryData>()
    private var categoryId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_custom_bottomsheet, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryList.add(
            CategoryData(
                "My Memorials",1
            )
        )
        categoryList.add(
            CategoryData(
                "Memorials",2
            )
        )
        categoryList.add(
            CategoryData(
                "Joined Memorials",3
            )
        )
        categoryList.add(
            CategoryData(
                "Received Requests",4
            )
        )

        binding.tvCategory.setOnClickListener {
            showCategoryPopup()
        }
    }

    private fun showCategoryPopup(){
        val bottomSheetDialog =  BottomSheetDialog(requireActivity())
        bottomSheetDialog.setContentView(R.layout.dialog_listview)
        val recyclerView = bottomSheetDialog.findViewById<RecyclerView>(R.id.rvList)
        val mCategoryAdapter = CategoryListAdapter( requireActivity(),
            categoryList, object: RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {
                    categoryId = categoryList[position]._id.toString()
                    categoryName.set(categoryList[position].name)
                    bottomSheetDialog.dismiss()
                }
            })
        recyclerView?.adapter = mCategoryAdapter

        bottomSheetDialog.show()
    }
}