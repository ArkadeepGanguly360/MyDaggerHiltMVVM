package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentAutoImageSliderBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentExpandableRecyclerviewBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class ExpandableRecyclerviewFragment : BaseFragment() {

    private lateinit var binding: FragmentExpandableRecyclerviewBinding

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
}