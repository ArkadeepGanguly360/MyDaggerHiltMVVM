package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.VideosAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomPopupBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentSwipeVideosLikeTiktokBinding
import com.development.mydaggerhiltmvvm.model.VideoItem
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import java.util.ArrayList

class SwipeVideosLikeTiktokFragment : BaseFragment() {

    private lateinit var binding: FragmentSwipeVideosLikeTiktokBinding
    var videoItemsList = ArrayList<VideoItem>()
    private var videosAdapter: VideosAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_swipe_videos_like_tiktok, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoItemsList.add(
            VideoItem(
                /* ContextCompat.getDrawable(requireActivity(), R.drawable.memorials_img1)!!,*/
                "https://heavenplusone-api.dedicateddevelopers.us/uploads/memorial/media_1627550774674_VID-20210719-WA0007.mp4",
                "Women In Tech",
                "International Women's Day 2019"
            )
        )
        videoItemsList.add(
            VideoItem(
                /*ContextCompat.getDrawable(requireActivity(), R.drawable.memorials_img2)!!,*/
                "https://heavenplusone-api.dedicateddevelopers.us/uploads/memorial/media_1627550774674_VID-20210719-WA0007.mp4",
                "Sasha Solomon",
                "How Sasha Solomon Became a Software Developer at Twitter"
            )
        )
        videoItemsList.add(
            VideoItem(
                /*  ContextCompat.getDrawable(requireActivity(), R.drawable.memorials_img1)!!,*/
                "https://heavenplusone-api.dedicateddevelopers.us/uploads/memorial/media_1627550774674_VID-20210719-WA0007.mp4",
                "Happy Hour Wednesday",
                "Depth-First Search Algorithm"
            )
        )

        videosAdapter = VideosAdapter(requireActivity(),videoItemsList)
        binding.viewPagerVideos.adapter = videosAdapter
    }
}