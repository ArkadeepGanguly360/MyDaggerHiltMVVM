package com.development.mydaggerhiltmvvm.view.activity

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ActivityPictureInPictureShowBinding
import com.development.mydaggerhiltmvvm.databinding.ActivitySplashBinding
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import kotlinx.android.synthetic.main.activity_picture_in_picture_show.*

class PictureInPictureShowActivity : BaseActivity() {

    private lateinit var binding: ActivityPictureInPictureShowBinding

    private val TAG:String = "PIP_TAG"
    private var videoUri: Uri? = null
    private var pictureInPictureParamsBuilder: PictureInPictureParams.Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = putContentView(R.layout.activity_picture_in_picture_show)

        //Todo get intent with url and pass in function to play video
        setVideoView(intent)

        //Todo init PictureInPictureParams, requires Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            pictureInPictureParamsBuilder = PictureInPictureParams.Builder()
        }

        //Todo handle click, enter PIP
        binding.pipBtn.setOnClickListener {
            pictureInPictureMode()
        }
    }

    private fun setVideoView(intent: Intent?) {
        val videoURL = intent!!.getStringExtra("videoURL")
        Log.d(TAG, "setVideoView: $videoURL")

        //Todo MediaController for video controls
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        videoUri = Uri.parse(videoURL)

        //Todo set media contrller to video view
        videoView.setMediaController(mediaController)
        //Todo set video uri to video view
        videoView.setVideoURI(videoUri)

        //Todo add video prepare listenrer
        videoView.setOnPreparedListener {mp ->
            //Todo video is prepared, play
            Log.d(TAG, "setVideoView: Video Prepared, playing...")
            mp.start()
        }
    }

    private fun pictureInPictureMode(){
        //Todo Requires Android O and higher
        Log.d(TAG, "pictureInPictureMode: Try to enter in PIP mode")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d(TAG, "pictureInPictureMode: Supports PIP")
            //Todo setup PIP height width
            val aspectRatio = Rational(videoView.width, videoView.height)
            pictureInPictureParamsBuilder!!.setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(pictureInPictureParamsBuilder!!.build())
        }
        else{
            Log.d(TAG, "pictureInPictureMode: Doesn't supports PIP")
            Toast.makeText(this, "Your device doesn't supports PIP", Toast.LENGTH_LONG).show()
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        //Todo when user presses home button, if not in PIP mode, enter in PIP, requires Android N and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Log.d(TAG, "onUserLeaveHint: was not in PIP")
            pictureInPictureMode()
        }
        else{
            Log.d(TAG, "onUserLeaveHint: Already in PIP")
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode){
            Log.d(TAG, "onPictureInPictureModeChanged: Entered PIP")
            //Todo hid pip button and actionbar
            pipBtn.visibility = View.GONE

        }
        else{
            Log.d(TAG, "onPictureInPictureModeChanged: Exited PIP")
            pipBtn.visibility = View.VISIBLE
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //Todo when 1st video is playing, and entered in PIP, clicked 2nd video play 2nd video
        Log.d(TAG, "onNewIntent: Play New Video")
        setVideoView(intent)
    }

    override fun onStop() {
        super.onStop()
        if (videoView.isPlaying){
            videoView.stopPlayback()
        }
    }
}