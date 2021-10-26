package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableField
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomRadioButtonBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomRadioButtonFragment : BaseFragment() {

    private lateinit var binding: FragmentCustomRadioButtonBinding
    var checkedValue = ObservableField("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_custom_radio_button, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btSubmit.onClick()
    }

    fun onCompoundButtonClicked(s: String?) {
        checkedValue.set(s)
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btSubmit.id -> {
                    checkedValue.get()
                    Log.e("CustomRadioButton","Result : ${checkedValue.get()}")
                }
            }
        }
    }
}