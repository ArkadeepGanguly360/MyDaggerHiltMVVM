package com.development.mydaggerhiltmvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.ActivityLoginBinding
import com.development.mydaggerhiltmvvm.databinding.ActivitySplashBinding
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardActivity
import com.development.mydaggerhiltmvvm.view.activity.introduction_activity.IntroductionActivity
import com.development.mydaggerhiltmvvm.view.activity.login_activity.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding = putContentView(R.layout.activity_splash)

        if (userPref.getUserData() != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                nextActivity(DashboardActivity::class.java, null, true)
            }, 1000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                nextActivity(IntroductionActivity::class.java, null, true)
            }, 2000)
        }
    }
}