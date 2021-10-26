package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentDifferentShapedImageviewBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentPictureInPictureBinding
import com.development.mydaggerhiltmvvm.view.activity.PictureInPictureShowActivity
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class PictureInPictureFragment : BaseFragment() {

    private lateinit var binding: FragmentPictureInPictureBinding

    private val videoOneUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    private val videoTwoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
    private val videoThreeUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_picture_in_picture, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btVideoOne.onClick()
        binding.btVideoTwo.onClick()
        binding.btVideoThree.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btVideoOne.id -> {
                    playVideo(videoOneUrl)
                }
                binding.btVideoTwo.id -> {
                    playVideo(videoTwoUrl)
                }
                binding.btVideoThree.id -> {
                    playVideo(videoThreeUrl)
                }
            }
        }
    }

    private fun playVideo(url:String){
        val intent = Intent()
        intent.setClass(requireActivity(), PictureInPictureShowActivity::class.java)
        intent.putExtra("videoURL", url)
        startActivity(intent)
    }
}