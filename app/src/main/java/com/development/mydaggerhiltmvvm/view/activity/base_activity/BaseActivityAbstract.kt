package com.development.mydaggerhiltmvvm.view.activity.base_activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivityAbstract : AppCompatActivity() {

    abstract fun createProgressDialog()
    abstract fun showProgressDialog()
    abstract fun showProgressDialog(title : String)
    abstract fun cancelProgressDialog()
    abstract fun createAlertDialog(
        title: String,
        msg: String,
        positiveButtonText: String,
        negativeButtonText: String,
        listener: (Boolean) -> Unit
    )

    abstract fun showAlertDialog()
    abstract fun showToast(msg: String)
    abstract fun showSnackBar(v: View, msg: String)
    abstract fun nextActivity(t: Class<*>, bundle: Bundle?, finishActivity: Boolean)

    abstract fun hideKeyboard(activity: Activity?)
    abstract fun hideKeyBoard(et: EditText?)
}