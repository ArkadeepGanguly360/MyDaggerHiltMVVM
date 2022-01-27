package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.FrameLayout
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentWhatsAppStoryViewBinding
import com.development.mydaggerhiltmvvm.model.WhatsAppStoryData
import com.development.mydaggerhiltmvvm.model.WhatsappStatus
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.squareup.picasso.Picasso
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.android.synthetic.main.fragment_whats_app_story_view.*


class WhatsAppStoryViewFragment : BaseFragment(), StoriesProgressView.StoriesListener {

    private lateinit var binding: FragmentWhatsAppStoryViewBinding
    private var storyList = ArrayList<WhatsAppStoryData>()
    private val mediaPlayerArrayList = ArrayList<View>()
    private var counter = 0
    var pressTime = 0L
    var limit = 500L
    var statusList = java.util.ArrayList<WhatsappStatus>()

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
        binding.stories.setStoriesListener(this)

        for (i in 0 until storyList.size) {
            statusList[i].isSeen = true
            if (storyList[i].mimeType.contains("video")) {
                mediaPlayerArrayList.add(getVideoView(i)!!)
            } else if (storyList[i].mimeType.contains("image")) {
                mediaPlayerArrayList.add(getImageView(i)!!)
            }
        }

        setStoryView(counter)
        binding.reverse.setOnClickListener{
            binding.stories.reverse()
        }
        reverse.setOnTouchListener(onTouchListener)
        binding.skip.setOnClickListener {
            binding.stories.skip()
        }
        skip.setOnTouchListener(onTouchListener)
    }

    private fun getStoryList() {

        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))
        statusList.add(WhatsappStatus(false))

        storyList.add(
            WhatsAppStoryData(
                "https://images.immediate.co.uk/production/volatile/sites/3/2019/04/Avengers-Endgame-Banner-2-de7cf60.jpg?quality=90&resize=620,413",
                "image/jpg",
                false,
                statusList
            )
        )
        storyList.add(
            WhatsAppStoryData(
                "https://img.cinemablend.com/filter:scale/quill/3/7/0/0/8/e/37008e36e98cd75101cf1347396eac8534871a19.jpg?mw=600",
                "image/jpg",
                false,
                statusList
            )
        )
        storyList.add(
            WhatsAppStoryData(
                "https://www.adgully.com/img/800/201711/spider-man-homecoming-banner.jpg",
                "image/jpg",
                false,
                statusList
            )
        )
        storyList.add(
            WhatsAppStoryData(
                "https://heavenplusone-api.dedicateddevelopers.us/uploads/memorial/media_1627550774674_VID-20210719-WA0007.mp4",
                "video/mp4",
                false,
                statusList
            )
        )
    }

    override fun onNext() {
        binding.stories.destroy()
        binding.videoView.removeAllViews()
        binding.progressBar.visibility = View.VISIBLE
        setStoryView(++counter)
    }

    override fun onPrev() {
        if ((counter - 1) < 0) return;
        binding.stories.destroy()
        binding.videoView.removeAllViews()
        binding.progressBar.visibility = View.VISIBLE
        setStoryView(--counter)
    }

    override fun onComplete() {
        goToNextFragment(
            R.id.action_whatsAppStoryViewFragment_to_dashboardFragment,
            null
        )
    }

    private fun getVideoView(position: Int): VideoView? {
        val video = VideoView(requireActivity())
        video.setVideoPath(storyList[position].mediaUrl)
        video.layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        return video
    }

    private fun getImageView(position: Int): ImageView? {
        val imageView = ImageView(requireActivity())
        imageView.layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        return imageView
    }

    private fun setStoryView(counter: Int) {
        val view = mediaPlayerArrayList[counter]
        binding.videoView.addView(view)
        if (view is VideoView) {
            val video = view
            video.setOnPreparedListener { mediaPlayer ->
                mediaPlayer.setOnInfoListener(MediaPlayer.OnInfoListener { mediaPlayer, i, i1 ->
                    Log.d("mediaStatus", "onInfo: =============>>>>>>>>>>>>>>>>>>>$i")
                    when (i) {
                        MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                            binding.progressBar.visibility = View.GONE
                            binding.stories.resume()
                            return@OnInfoListener true
                        }
                        MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.stories.pause()
                            return@OnInfoListener true
                        }
                        MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.stories.pause()
                            return@OnInfoListener true
                        }
                        MediaPlayer.MEDIA_ERROR_TIMED_OUT -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.stories.pause()
                            return@OnInfoListener true
                        }
                        MediaPlayer.MEDIA_INFO_AUDIO_NOT_PLAYING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.stories.pause()
                            return@OnInfoListener true
                        }
                    }
                    false
                })
                video.start()
                binding.progressBar.visibility = View.GONE
                binding.stories.setStoryDuration(mediaPlayer.duration.toLong())
                binding.stories.startStories(counter)
            }
        } else if (view is ImageView) {
            val image: ImageView = view
            binding.progressBar.visibility = View.GONE
            Glide.with(this).load(storyList.get(counter).mediaUrl)
                .addListener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        @Nullable e: GlideException?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(
                            requireActivity(),
                            "Failed to load media...",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        binding.stories.setStoryDuration(5000)
                        binding.stories.startStories(counter)
                        return false
                    }
                }).into(image)
        }
    }
}