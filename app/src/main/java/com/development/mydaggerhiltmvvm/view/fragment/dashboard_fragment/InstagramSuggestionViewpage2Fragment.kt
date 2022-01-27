package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.CompositePageTransformer
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.AutoImageSliderViewPagerAdapter
import com.development.mydaggerhiltmvvm.adapter.InstagramSuggestionViewPagerAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentInstagramSuggestionViewpage2Binding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.AutoImageSliderData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class InstagramSuggestionViewpage2Fragment : BaseFragment() {

    private lateinit var binding: FragmentInstagramSuggestionViewpage2Binding

    var sliderList = ArrayList<AutoImageSliderData>()
    private lateinit var instagramSuggestionViewPagerAdapter: InstagramSuggestionViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(
            R.layout.fragment_instagram_suggestion_viewpage2,
            layoutInflater,
            container
        )
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

        initViewPagerAdapter()

        binding.viewpager2.clipToPadding = false
        binding.viewpager2.clipChildren = false
        binding.viewpager2.offscreenPageLimit = 3

        val transformer = CompositePageTransformer()
        transformer.addTransformer { page, position ->
            val a = 1 - Math.abs(position)
            page.scaleY = 0.85f + a * 0.15f
        }

        binding.viewpager2.setPageTransformer(transformer)
    }

    private fun initViewPagerAdapter() {
        instagramSuggestionViewPagerAdapter = InstagramSuggestionViewPagerAdapter(
            requireActivity(),
            sliderList,
            object : RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {
                    goToNextFragment(
                        R.id.action_instagramSuggestionViewpage2Fragment_to_dashboardFragment,
                        null
                    )
                }
            })

        binding.viewpager2.apply {
            adapter = instagramSuggestionViewPagerAdapter
        }
    }
}