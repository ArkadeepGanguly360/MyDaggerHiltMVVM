package com.development.mydaggerhiltmvvm.view.fragment.login_fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentLoginBinding
import com.development.mydaggerhiltmvvm.model.LoginResponse
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.development.mydaggerhiltmvvm.view.activity.login_activity.LoginViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private lateinit var  binding : FragmentLoginBinding
    private lateinit var  loginViewModel: LoginViewModel

    private var isPasswordVisible = false

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

       /* observeNetworkStatus()
        observeValidateMsg()
        observeLoginDetails()*/

        binding.btLogin.onClick()
        binding.tvSignup.onClick()
        binding.imgPasswordCheck.onClick()
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.btLogin.id -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        loginViewModel.login()
                    }
                }
                binding.tvSignup.id -> {
                    goToNextFragment(
                        R.id.action_loginFragment_to_signupFragment,
                        null
                    )
                }
                binding.imgPasswordCheck.id -> {
                    if (!isPasswordVisible)
                        binding.etPassword.transformationMethod = PasswordTransformationMethod()
                    else
                        binding.etPassword.transformationMethod = null
                    isPasswordVisible = !isPasswordVisible
                }
            }
        }
    }

    /*   private fun observeLoginDetails() {
        var observer = Observer<LoginResponse> { response ->
            response.let {
                showToast(response.message)
                userPref.setUserToken(response.token)
                userPref.setUserLogin(response.isLoggedIn)
                userPref.saveUserData(response.data.toString())
                val bundle = Bundle()
                baseActivity.nextActivity(DashboardActivity::class.java, bundle, true)
            }
        }
        loginViewModel.observeLoginDetails().observe(viewLifecycleOwner, observer)
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
    }*/
}