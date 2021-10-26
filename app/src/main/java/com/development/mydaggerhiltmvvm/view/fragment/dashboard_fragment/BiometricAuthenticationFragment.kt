package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentBiometricAuthenticationBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentDifferentShapedImageviewBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import java.util.concurrent.Executor

class BiometricAuthenticationFragment : BaseFragment() {

    private lateinit var binding: FragmentBiometricAuthenticationBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_biometric_authentication, layoutInflater, container)
        binding.fragment = this

        //Todo init biometric
        executor = ContextCompat.getMainExecutor(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        biometricPrompt = BiometricPrompt(requireActivity(), executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)

                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    //Todo user clicked negative button
                    Toast.makeText(requireActivity(), "Negative Button Clicked", Toast.LENGTH_SHORT).show()

                } else {
                    //Todo auth error, stop tasks that requires auth
                    binding.authStatusTv.text = "Authentication Error: $errString"
                    Toast.makeText(requireActivity(), "Authentication Error: $errString", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                //Todo auth succeed, do tasks that requires auth
                binding.authStatusTv.text = "Auth succeed...!"
                Toast.makeText(requireActivity(), "Auth succeed...!", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                //Todo auth failed, stop tasks that requires auth
                binding.authStatusTv.text = "Auth failed...!"
                Toast.makeText(requireActivity(), "Auth failed...!", Toast.LENGTH_SHORT).show()
            }
        })

        //Todo set properties like title and description on auth dialog
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint authentication")
            .setNegativeButtonText("Use App Password instead")
            .build()

        //Todo handle click, start authtenitcation dialog
        binding.authBtn.setOnClickListener {
            //Todo show auth dialog
            biometricPrompt.authenticate(promptInfo)
        }
    }
}