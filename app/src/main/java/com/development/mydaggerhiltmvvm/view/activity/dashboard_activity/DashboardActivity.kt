package com.development.mydaggerhiltmvvm.view.activity.dashboard_activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.os.postDelayed
import androidx.core.view.GravityCompat
import androidx.databinding.ObservableField
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ActivityDashboardBinding
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar_main.*


@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var backPressedOnce = false

    val toolbarVisibility = ObservableField(View.VISIBLE)
    val bottomBarVisibility = ObservableField(View.VISIBLE)
    val toolbarNotificationVisibility = ObservableField(View.VISIBLE)
    val toolbarBackVisibility = ObservableField(View.GONE)
    val toolbarMenuVisibility = ObservableField(View.VISIBLE)

    private val navigationDestListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            toolbarVisibility.set(View.VISIBLE)
            bottomBarVisibility.set(View.VISIBLE)
            toolbarNotificationVisibility.set(View.VISIBLE)
            toolbarBackVisibility.set(View.GONE)
            toolbarMenuVisibility.set(View.VISIBLE)
            setDrawerLockMethod(false)

            if (destination.label.toString().isNotEmpty()) {
                binding.mainContent.toolbarLayout.tvToolbarText.text = destination.label.toString()
            }

            when (destination.id) {
                R.id.dashboardFragment -> {
                }
                R.id.mapFragment -> {
                }
                R.id.profileFragment -> {
                }
                R.id.notificationFragment -> {
                }
                R.id.friendDetailsFragment -> {
                    toolbarBackVisibility.set(View.VISIBLE)
                    toolbarMenuVisibility.set(View.GONE)
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = putContentView(R.layout.activity_dashboard)
        binding.dashboardActivity = this
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        dashboardViewModel.initActivity(this, this)
        initNavController()

        binding.mainContent.toolbarLayout.imgToolbarMenu.onClick()
        binding.mainContent.toolbarLayout.imgToolbarBack.onClick()
        binding.mainContent.toolbarLayout.imgToolbarNotification.onClick()
    }

    private fun initNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.dashboardNavigationFragment) as NavHostFragment
        navController = navHostFragment.navController

        /*Todo set navigation graph*/
        val navGraph = navController.navInflater.inflate(R.navigation.dashboard_navigation)
        navGraph.startDestination = R.id.dashboardFragment

        /*Todo connect nav controller with bottom navigation view*/
        binding.mainContent.bottomNavigation.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.mapFragment,
                R.id.profileFragment,
                R.id.notificationFragment,
                R.id.friendDetailsFragment
            )
        )

       /*Todo connect nav controller with Toolbar*/
        setSupportActionBar(binding.mainContent.toolbarLayout.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(
            navController,
            appBarConfiguration
        )

       /*Todo navigation drawer attached with navController*/
        binding.navView.setupWithNavController(navController)

       /*Todo on nav controller destination change listener*/
        navController.addOnDestinationChangedListener(navigationDestListener)
    }

    private fun setDrawerOpenStatus(toOpen: Boolean) {
        if (toOpen)
            binding.drawerLayout.openDrawer(GravityCompat.START)
        else
            binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun setDrawerLockMethod(value: Boolean) {
        if (value)
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        else
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun View.onClick() {
        this.setOnClickListener {
            when (it.id) {
                binding.mainContent.toolbarLayout.imgToolbarMenu.id -> {
                    setDrawerOpenStatus(true)
                }
                binding.mainContent.toolbarLayout.imgToolbarBack.id -> {
                    navController.popBackStack()
                }
                binding.mainContent.toolbarLayout.imgToolbarNotification.id -> {
                    navController.navigate(R.id.notificationFragment)
                }
            }
        }
    }

    override fun onBackPressed() {
        when {
            navController.graph.startDestination == navController.currentDestination?.id -> {
                if (backPressedOnce) {
                    finishAffinity()
                    return
                }
                backPressedOnce = true
                Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
                Handler().postDelayed(2000) {
                    backPressedOnce = false
                }
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        navHostFragment.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.onActivityResult(
                requestCode,
                resultCode,
                data
            )
        }
    }
}