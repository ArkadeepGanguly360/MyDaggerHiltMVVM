package com.development.mydaggerhiltmvvm.view.activity.introduction_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.ViewPagerAdapter
import com.development.mydaggerhiltmvvm.databinding.ActivityIntroductionBinding
import com.development.mydaggerhiltmvvm.databinding.ActivitySplashBinding
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity

class IntroductionActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroductionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = putContentView(R.layout.activity_introduction)

        val fragmentList = arrayListOf<Fragment>(
            IntroductionFirstScreen(),
            IntroductionSecondScreen(),
            IntroductionThirdScreen()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            this.supportFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter
    }
}