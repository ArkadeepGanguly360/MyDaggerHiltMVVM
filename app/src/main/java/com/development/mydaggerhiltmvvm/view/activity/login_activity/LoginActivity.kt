package com.development.mydaggerhiltmvvm.view.activity.login_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ActivityLoginBinding
import com.development.mydaggerhiltmvvm.databinding.ActivityMainBinding
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.development.mydaggerhiltmvvm.view.activity.main_activity.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.initActivity(this,this)
        initNavController()
    }

    private fun initNavController() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.loginNavigationFragment) as NavHostFragment
        navController = findNavController(R.id.loginNavigationFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.login_navigation)
        navGraph.startDestination = R.id.loginFragment
        navController.graph = navGraph
    }
}