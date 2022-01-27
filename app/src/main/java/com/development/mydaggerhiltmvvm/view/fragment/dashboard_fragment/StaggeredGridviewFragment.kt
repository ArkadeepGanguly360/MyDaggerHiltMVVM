package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.MyFriendListAdapter
import com.development.mydaggerhiltmvvm.adapter.PhoneContactListAdapter
import com.development.mydaggerhiltmvvm.adapter.StaggeredGridviewAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentStaggeredGridviewBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousSliderBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.StaggeredGridData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class StaggeredGridviewFragment : BaseFragment() {

    private lateinit var binding: FragmentStaggeredGridviewBinding
    val itemList: MutableList<StaggeredGridData> = mutableListOf()
    private lateinit var staggeredGridviewAdapter: StaggeredGridviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_staggered_gridview, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStaggeredListAdapter()

        itemList.add(StaggeredGridData(R.drawable.leeminho))
        itemList.add(StaggeredGridData(R.drawable.leejongsuk))
        itemList.add(StaggeredGridData(R.drawable.chaeunwoo))
        itemList.add(StaggeredGridData(R.drawable.seokangjoon))
        itemList.add(StaggeredGridData(R.drawable.kimsoohyun))
        itemList.add(StaggeredGridData(R.drawable.parkseojoon))
        itemList.add(StaggeredGridData(R.drawable.seoinguk))
        itemList.add(StaggeredGridData(R.drawable.jichangwook))
        itemList.add(StaggeredGridData(R.drawable.yooseungho))
        itemList.add(StaggeredGridData(R.drawable.leeseunggi))
    }

    private fun initStaggeredListAdapter() {
        staggeredGridviewAdapter = StaggeredGridviewAdapter(
            requireActivity(),
            itemList,
            object : RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {

                }
            })

        binding.recycleGrid.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = staggeredGridviewAdapter
        }
    }
}