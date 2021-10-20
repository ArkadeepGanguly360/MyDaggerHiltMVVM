package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentProfileBinding
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.util.ArrayList
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    //Todo kotlin permission
    private var listPermission = ArrayList<String>()

    private var imageFrom: Int? = null
    private var imageFilename: String? = null
    private var imageFile: Bitmap? = null
    private var imageUri: Uri? = null
    private var imageCount: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            listPermission.addAll(MyConstant.APP_PERMISSION.ALL_PERMISSION)
        else
            listPermission.addAll(MyConstant.APP_PERMISSION.ABOVE_API_29_PERMISSION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = putContentView(R.layout.fragment_profile, layoutInflater, container)
            dashboardViewModel =
                ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
            binding.viewModel = dashboardViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* observeNetworkStatus()
         observeValidateMsg()*/

        binding.profileImg.onClick()
        binding.buttonUpdateProfile.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.profileImg.id -> {
                    getImageFromUserSelected(listPermission) { from: Int,
                                                               filename: String?,
                                                               file: Bitmap?,
                                                               uri: Uri?,
                                                               count: Int ->
                        imageFrom = from
                        imageFilename = filename
                        imageFile = file
                        imageUri = uri
                        imageCount = count
                        binding.profileImg.setImageURI(uri)
                    }
                }
                binding.buttonUpdateProfile.id -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (imageFile != null) {
                            createMultipartImageFile(imageFilename, imageFile, "profile_image") {
                                dashboardViewModel.profileImage = it
                            }
                        }
                        dashboardViewModel.sendProfileUpdateData()
                    }
                }
            }
        }
    }

    /* private fun observeNetworkStatus(){
       var observer = Observer<String> { response ->
           showToast(response)
       }
       dashboardViewModel.observeNetworkStatus().observe(viewLifecycleOwner, observer)
   }

   private fun observeValidateMsg(){
       var observer = Observer<String> { response ->
           showToast(response)
       }
       dashboardViewModel.observeValidateMsg().observe(viewLifecycleOwner, observer)
   }*/
}