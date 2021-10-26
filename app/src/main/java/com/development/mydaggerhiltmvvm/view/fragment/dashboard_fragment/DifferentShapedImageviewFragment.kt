package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentCustomRadioButtonBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentDifferentShapedImageviewBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.google.android.material.shape.CornerFamily

class DifferentShapedImageviewFragment : BaseFragment() {

    private lateinit var binding: FragmentDifferentShapedImageviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_different_shaped_imageview, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Todo setup properties for ShapeableImageView
        val shapeAppearanceModel = binding.shapeableIv.shapeAppearanceModel.toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 125f)
            .build()

        //Todo set ShapeableImageView
        binding.shapeableIv.shapeAppearanceModel = shapeAppearanceModel
    }
}