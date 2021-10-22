package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomPopupBinding
import com.development.mydaggerhiltmvvm.databinding.PopupScreenBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import java.util.*

class CustomPopupFragment : BaseFragment() {

    private lateinit var binding: FragmentCustomPopupBinding

    private var createFullScreenDialog: Dialog? = null
    private var createNormalDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_custom_popup, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createFullScreenPopup()
        createNormalPopup()

        binding.btFullPopup.onClick()
        binding.btNormalPopup.onClick()
    }

    private fun createFullScreenPopup() {
        createFullScreenDialog = Dialog(requireActivity())
        createFullScreenDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        createFullScreenDialog!!.setCancelable(true)
        createFullScreenDialog!!.setCanceledOnTouchOutside(true)

        var popupFullScreenBinding: PopupScreenBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.popup_screen, binding.root as ViewGroup,
            false)

        createFullScreenDialog!!.setContentView(popupFullScreenBinding.root)

        popupFullScreenBinding.btSubmit.setOnClickListener {
            popupFullScreenBinding.etTitle.text
            createFullScreenDialog!!.dismiss()
        }

        createFullScreenDialog!!.setOnDismissListener {
            popupFullScreenBinding.etTitle.setText("")
        }

        val window = createFullScreenDialog!!.window
        Objects.requireNonNull(window)!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun createNormalPopup() {
        createNormalDialog = Dialog(requireActivity())
        createNormalDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        createNormalDialog!!.setCancelable(true)
        createNormalDialog!!.setCanceledOnTouchOutside(true)

        var popupBinding: PopupScreenBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.popup_screen, binding.root as ViewGroup,
            false)

        createNormalDialog!!.setContentView(popupBinding.root)

        popupBinding.btSubmit.setOnClickListener {
            createNormalDialog!!.dismiss()
        }

        createNormalDialog!!.setOnDismissListener {
            popupBinding.etTitle.setText("")
        }

        val window = createNormalDialog!!.window
        Objects.requireNonNull(window)!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btFullPopup.id -> {
                    if (createFullScreenDialog != null)
                        createFullScreenDialog!!.show()
                }
                binding.btNormalPopup.id -> {
                    if (createNormalDialog != null)
                        createNormalDialog!!.show()
                }
            }
        }
    }
}