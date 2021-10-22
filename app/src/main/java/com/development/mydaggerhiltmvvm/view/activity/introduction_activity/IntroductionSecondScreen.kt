package com.development.mydaggerhiltmvvm.view.activity.introduction_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentIntroductionSecondScreenBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentIntroductionThirdScreenBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_introduction.*
import kotlinx.android.synthetic.main.fragment_introduction_second_screen.*

class IntroductionSecondScreen : BaseFragment() {

    private lateinit var binding: FragmentIntroductionSecondScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_introduction_second_screen, layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next2.setOnClickListener {
            requireActivity().viewPager.currentItem = 2
        }
    }
}