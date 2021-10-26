package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousIntentBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentVariousSliderBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class VariousSliderFragment : BaseFragment() {

    private lateinit var binding: FragmentVariousSliderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_various_slider, layoutInflater, container)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sliderRange.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }
    }
}