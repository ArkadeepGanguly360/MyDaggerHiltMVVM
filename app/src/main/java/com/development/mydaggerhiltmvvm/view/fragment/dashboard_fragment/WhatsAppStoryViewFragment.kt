package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentAutoImageSliderBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousIntentBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentWhatsAppStoryViewBinding
import com.development.mydaggerhiltmvvm.model.AutoImageSliderData
import com.development.mydaggerhiltmvvm.model.WhatsAppStoryData
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.squareup.picasso.Picasso
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.android.synthetic.main.fragment_whats_app_story_view.*
import java.lang.Exception

class WhatsAppStoryViewFragment : BaseFragment(), StoriesProgressView.StoriesListener {

    private lateinit var binding: FragmentWhatsAppStoryViewBinding
    var storyList = ArrayList<WhatsAppStoryData>()

    var pressTime = 0L
    var limit = 500L
    private var counter = 0

    val picasso = Picasso.get()

    private val onTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    pressTime = System.currentTimeMillis()
                    binding.stories.pause()
                    return false
                }
                MotionEvent.ACTION_UP -> {
                    val now = System.currentTimeMillis()
                    binding.stories.resume()
                    return limit < now - pressTime
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_whats_app_story_view, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getStoryList()

        binding.stories.setStoriesCount(storyList.size)
        binding.stories.setStoryDuration(3000L)
        binding.stories.setStoriesListener(this)
        binding.stories.startStories(counter)

        picasso.load(storyList[counter].image)
            .error(R.drawable.abc)
            .into(binding.image)

        binding.reverse.setOnClickListener {
            binding.stories.reverse()
        }

        binding.reverse.setOnTouchListener(onTouchListener)

        binding.skip.setOnClickListener {
            binding.stories.skip();
        }

        binding.skip.setOnTouchListener(onTouchListener);
    }

    private fun getStoryList() {
        storyList.add(
            WhatsAppStoryData(
                "https://images.immediate.co.uk/production/volatile/sites/3/2019/04/Avengers-Endgame-Banner-2-de7cf60.jpg?quality=90&resize=620,413"
            )
        )
        storyList.add(
            WhatsAppStoryData(
                "https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600"
            )
        )
        storyList.add(
            WhatsAppStoryData(
                "https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg"
            )
        )
    }

    override fun onNext() {
        picasso.load(storyList[++counter].image)
            .error(R.drawable.abc)
            .into(binding.image)
    }

    override fun onPrev() {
        if (counter - 1 < 0) return
        picasso.load(storyList[--counter].image)
            .error(R.drawable.abc)
            .into(binding.image)
    }

    override fun onComplete() {
        goToNextFragment(
            R.id.action_whatsAppStoryViewFragment_to_dashboardFragment,
            null
        )
    }
}