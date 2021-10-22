package com.development.mydaggerhiltmvvm.view.activity.introduction_activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentIntroductionFirstScreenBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentIntroductionThirdScreenBinding
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.development.mydaggerhiltmvvm.view.activity.login_activity.LoginActivity
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_introduction_third_screen.*

class IntroductionThirdScreen : BaseFragment() {

    private lateinit var binding: FragmentIntroductionThirdScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_introduction_third_screen, layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finish.setOnClickListener {
            baseActivity.nextActivity(LoginActivity::class.java, null, true)
        }
    }
}