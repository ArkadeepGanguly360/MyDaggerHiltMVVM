package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentLottieProgressDialogBinding
import com.development.mydaggerhiltmvvm.util.common_utils.UtilExtensions.isVisible
import com.development.mydaggerhiltmvvm.util.lottieProgressDialog.LottieProgressDialog
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class LottieProgressDialogFragment : BaseFragment() {

    private lateinit var binding: FragmentLottieProgressDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_lottie_progress_dialog, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bt1.onClick()
        binding.bt2.onClick()
        binding.bt3.onClick()
        binding.bt4.onClick()
        binding.bt5.onClick()
        binding.bt6.onClick()
        binding.bt7.onClick()
        binding.bt8.onClick()
        binding.bt9.onClick()
        binding.bt10.onClick()
    }

    private fun openDialog(sample: String) {
        LottieProgressDialog(
            context = requireActivity(),
            isCancel = true,
            dialogWidth = null,
            dialogHeight = null,
            animationViewWidth = null,
            animationViewHeight = null,
            fileName = sample,
            title = null,
            titleVisible = null
        ).show()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.bt1.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_1)
                }
                binding.bt2.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_2)
                }
                binding.bt3.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_3)
                }
                binding.bt4.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_4)
                }
                binding.bt5.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_5)
                }
                binding.bt6.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_6)
                }
                binding.bt7.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_7)
                }
                binding.bt8.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_8)
                }
                binding.bt9.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_9)
                }
                binding.bt10.id -> {
                    openDialog(LottieProgressDialog.SAMPLE_10)
                }
            }
        }
    }
}