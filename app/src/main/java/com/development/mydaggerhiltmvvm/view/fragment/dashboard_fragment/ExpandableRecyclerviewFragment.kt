package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.ExpandableParentAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentExpandableRecyclerviewBinding
import com.development.mydaggerhiltmvvm.interfaces.OnExpandableListItemClickListener
import com.development.mydaggerhiltmvvm.model.ExpandableParentData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class ExpandableRecyclerviewFragment : BaseFragment() {

    private lateinit var binding: FragmentExpandableRecyclerviewBinding
    var parentList = ArrayList<ExpandableParentData>()
    private lateinit var expandableParentAdapter: ExpandableParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_expandable_recyclerview, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initExpandableParentAdapter()

        parentList.add(
            ExpandableParentData(
                "Dipanwita","0"
            )
        )
        parentList.add(
            ExpandableParentData(
                "Nilam","1"
            )
        )
        parentList.add(
            ExpandableParentData(
                "Tapasree","2"
            )
        )
        parentList.add(
            ExpandableParentData(
                "Poulami","3"
            )
        )
    }

    private fun initExpandableParentAdapter() {
        expandableParentAdapter = ExpandableParentAdapter(
            requireActivity(),
            parentList,
            object : OnExpandableListItemClickListener {
                override fun onIemClicked(view: View?, parentPosition: Int?, childPosition: Int?) {
                  Log.e("Tag","Parent Position is : $parentPosition \nChild position is : $childPosition")
                }
            })

        binding.recycleParent.apply {
            adapter = expandableParentAdapter
        }
    }
}