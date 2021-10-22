package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.development.mydaggerhiltmvvm.BuildConfig
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousIntentBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class VariousIntentFragment : BaseFragment() {

    private lateinit var binding: FragmentVariousIntentBinding
    private val phNo = "6297317879"
    private val latitude = "22.4815"
    private val longitude = "88.3864"
    private var webUrl = "https://www.youtube.com/"
    private val packagename = "com.geoff.catchup"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_various_intent, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btCall.onClick()
        binding.btLocation.onClick()
        binding.btWebsite.onClick()
        binding.btMsg.onClick()
        binding.btWhatsapp.onClick()
        binding.btShare.onClick()
        binding.btGooglePlaystore.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btCall.id -> {
                    val intent = Intent()
                    intent.action = Intent.ACTION_DIAL
                    intent.data = Uri.parse("tel:$phNo")
                    startActivity(intent)
                }
                binding.btLocation.id -> {
                    val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
                binding.btWebsite.id -> {
                    if (!webUrl.contains("http://") && !webUrl.contains("https://"))
                        webUrl = "https://$webUrl"
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
                    startActivity(browserIntent)
                }
                binding.btMsg.id -> {
                    val smsIntent = Intent(Intent.ACTION_VIEW)

                    smsIntent.data = Uri.parse("smsto:")
                    smsIntent.type = "vnd.android-dir/mms-sms"
                    smsIntent.putExtra("address", phNo)
                    smsIntent.putExtra("sms_body", "Test ")

                    try {
                        startActivity(smsIntent)
                        requireActivity().finish()
                        Log.i("Finished sending SMS...", "")
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            requireActivity(),
                            "SMS faild, please try again later.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                binding.btWhatsapp.id -> {
                    val url = "https://api.whatsapp.com/send?phone=$phNo"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }
                binding.btShare.id -> {
                    try {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name")
                        var shareMessage = "\nLet me recommend you this application\n\n"
                        shareMessage =
                            """
                            ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                            
                            
                            """.trimIndent()
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                        startActivity(Intent.createChooser(shareIntent, "choose one"))
                    } catch (e: Exception) {
                        //e.toString();
                    }
                }
                binding.btGooglePlaystore.id -> {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packagename")))
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packagename")))
                    }
                }
            }
        }
    }
}