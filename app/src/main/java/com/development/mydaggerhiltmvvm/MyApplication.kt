package com.development.mydaggerhiltmvvm

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MyApplication: Application(){

    companion object {
        var myApplication: MyApplication? = null

        fun getInstance(): MyApplication {
            return myApplication!!
        }

        fun applicationContext(): Context {
            return myApplication!!.applicationContext
        }
    }

    init {
        myApplication = this
    }
}