package com.development.mydaggerhiltmvvm.view.fragment.login_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentLoginBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentSignupBinding
import com.development.mydaggerhiltmvvm.view.activity.login_activity.LoginViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class SignupFragment : BaseFragment() {

    private lateinit var  binding : FragmentSignupBinding
    private lateinit var  loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_signup, layoutInflater, container)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        return binding.root
    }
}