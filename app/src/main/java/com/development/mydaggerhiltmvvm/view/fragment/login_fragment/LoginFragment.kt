package com.development.mydaggerhiltmvvm.view.fragment.login_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentLoginBinding
import com.development.mydaggerhiltmvvm.view.activity.login_activity.LoginViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {

    private lateinit var  binding : FragmentLoginBinding
    private lateinit var  loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_login, layoutInflater, container)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNetworkStatus()
        observeValidateMsg()

        binding.btLogin.onClick()
        binding.tvSignup.onClick()
    }

    private fun observeNetworkStatus(){
        var observer = Observer<String> { response ->
            showToast(response)
        }
        loginViewModel.observeNetworkStatus().observe(viewLifecycleOwner, observer)
    }

    private fun observeValidateMsg(){
        var observer = Observer<String> { response ->
            showToast(response)
        }
        loginViewModel.observeValidateMsg().observe(viewLifecycleOwner, observer)
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btLogin.id -> {
                    GlobalScope.launch {
                        loginViewModel.login()
                    }
                }
                binding.tvSignup.id -> {
                    goToNextFragment(
                        R.id.action_loginFragment_to_signupFragment,
                        null
                    )
                }
            }
        }
    }

}