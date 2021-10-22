package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentEmojiKeyboardLikeWhatsAppBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentReadMoreTextviewBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.vanniktech.emoji.EmojiPopup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*Todo Reference : https://github.com/vanniktech/Emoji  (EMOJI)*/
/*Todo Reference: https://developers.giphy.com/docs/sdk#android  (GIF)*/

class EmojiKeyboardLikeWhatsAppFragment : BaseFragment() {

    private lateinit var binding: FragmentEmojiKeyboardLikeWhatsAppBinding
    private val emojiPopup by lazy { EmojiPopup.Builder.fromRootView(binding.imgSmile).build(binding.etWriteSomething)  }
    private var gifUrl=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_emoji_keyboard_like_whats_app, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgSmile.onClick()
        binding.etWriteSomething.onClick()
        binding.imgGif.onClick()
        binding.imgSend.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.imgSmile.id -> {
                    emojiPopup.toggle()
                }
                binding.etWriteSomething.id -> {
                    emojiPopup.dismiss()
                }
                binding.imgGif.id -> {
                    showGifDialog("post", 0)
                }
                binding.imgSend.id -> {
                    binding.tvShow.text = binding.etWriteSomething.text?.trim()
                }
            }
        }
    }


    private fun showGifDialog(type:String?, position:Int){
        val settings =  GPHSettings()
        val dialog = GiphyDialogFragment.newInstance(settings)
        dialog.gifSelectionListener = object : GiphyDialogFragment.GifSelectionListener{
            override fun didSearchTerm(term: String) {

            }
            override fun onDismissed(selectedContentType: GPHContentType) {

            }
            override fun onGifSelected(
                media: Media, searchTerm: String?, selectedContentType: GPHContentType
            ) {
                gifUrl = media.images.original?.gifUrl?:""
                when(type){
                    "post"->{
                        Glide.with(requireActivity())
                            .asGif()
                            .load(media.images.original?.gifUrl?:"")
                            .apply(RequestOptions().placeholder(R.mipmap.ic_launcher))
                            .into(binding.imgShow)

                        binding.imgShow.visibility = View.VISIBLE
                    }
                }
            }
        }
        dialog.show(requireActivity().supportFragmentManager, "giphy_dialog")
    }
}