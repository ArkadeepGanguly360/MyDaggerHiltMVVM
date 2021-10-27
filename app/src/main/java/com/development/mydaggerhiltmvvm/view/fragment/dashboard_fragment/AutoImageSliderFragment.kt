package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.AutoImageSliderViewPagerAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentAutoImageSliderBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousIntentBinding
import com.development.mydaggerhiltmvvm.model.AutoImageSliderData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList

class AutoImageSliderFragment : BaseFragment() {

    private lateinit var binding: FragmentAutoImageSliderBinding

    var sliderList = ArrayList<AutoImageSliderData>()
    private lateinit var autoImageSliderViewPagerAdapter: AutoImageSliderViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_auto_image_slider, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sliderList.add(
            AutoImageSliderData(
                "https://images.immediate.co.uk/production/volatile/sites/3/2019/04/Avengers-Endgame-Banner-2-de7cf60.jpg?quality=90&resize=620,413",
                "Iron Man",
            )
        )
        sliderList.add(
            AutoImageSliderData(
                "https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600",
                "Captain America",
            )
        )
        sliderList.add(
            AutoImageSliderData(
                "https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg",
                "Spider Man",
            )
        )

        autoImageSliderViewPagerAdapter = AutoImageSliderViewPagerAdapter(requireActivity(), sliderList)
        binding.viewpager2.adapter = autoImageSliderViewPagerAdapter

        //Todo Auto Slider Timer
        val timer = Timer()
        timer.scheduleAtFixedRate(
            this.AutoSliderTimer(),
            2000,
            3000
        )

        TabLayoutMediator(binding.tab, binding.viewpager2) { tab, position ->
        }.attach()
    }

    inner class AutoSliderTimer : TimerTask() {
        override fun run() {
            requireActivity().runOnUiThread(Runnable {
                if (binding.viewpager2.currentItem < sliderList.size - 1) {
                    binding.viewpager2.currentItem = binding.viewpager2.currentItem + 1
                } else
                    binding.viewpager2.currentItem = 0
            })
        }
    }
}