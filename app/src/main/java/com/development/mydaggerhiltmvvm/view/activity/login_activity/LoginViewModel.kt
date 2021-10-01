package com.development.mydaggerhiltmvvm.view.activity.login_activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.development.mydaggerhiltmvvm.interfaces.WebInterface
import com.development.mydaggerhiltmvvm.restService.AllWebServiceCall
import com.development.mydaggerhiltmvvm.restService.RestInterface
import com.development.mydaggerhiltmvvm.util.NetworkHelper
import com.development.mydaggerhiltmvvm.util.UserSharedPrefrence
import com.development.mydaggerhiltmvvm.util.common_utils.UtilFile
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.development.mydaggerhiltmvvm.view.activity.main_activity.MainActivity
import org.json.JSONException
import org.json.JSONObject

class LoginViewModel @ViewModelInject constructor(
    private var restInterface: RestInterface,
    private var allWebServiceCall: AllWebServiceCall,
    private val networkHelper: NetworkHelper,
    private val userPref: UserSharedPrefrence) : ViewModel(), WebInterface {

    private lateinit var baseActivity: BaseActivity
    private lateinit var loginActivity: LoginActivity

    var progressbarVisibility = ObservableField(View.GONE)
    var email = ObservableField("")
    var password = ObservableField("")

    private var validateMessage = MutableLiveData<String>()
    private var networkStatus = MutableLiveData<String>()

    fun initActivity(baseActivity: BaseActivity, loginActivity: LoginActivity) {
        this.baseActivity = baseActivity
        this.loginActivity = loginActivity
    }

    fun observeValidateMsg(): MutableLiveData<String> {
        return validateMessage
    }

    fun observeNetworkStatus(): MutableLiveData<String> {
        return networkStatus
    }

    suspend fun login() {
        if (loginValidation()) {
            if (networkHelper.isNetworkConnected()) {
                progressbarVisibility.set(View.VISIBLE)
                val map = HashMap<String, String>()
                map["email"] = email.get()!!
                map["password"] = password.get()!!
                val query = restInterface.signIn(map)
                allWebServiceCall.webServiceCall(query, this, "signIn")
            } else {
                networkStatus.postValue("No Internet Connection")
            }
        }
    }

    override fun <E> resultSuccess(t: E, method_name: String?) {
        try {
            val jsonObject = JSONObject(t.toString())
            when (method_name) {
                "signIn" -> {
                    baseActivity.showToast(jsonObject.optString("message"))
                    userPref.setUserToken(jsonObject.optString("token"))
                    userPref.setUserLogin(jsonObject.optBoolean("isLoggedIn"))
                    userPref.saveUserData(jsonObject.optJSONObject("data")!!.toString())
                    val bundle = Bundle()
                    baseActivity.nextActivity(MainActivity::class.java, bundle, true)
                }
            }
        } catch (e: JSONException) {
        } finally {
            progressbarVisibility.set(View.GONE)
        }
    }

    override fun failureSuccess(s: String?) {
        progressbarVisibility.set(View.GONE)
        try {
            val jsonObject = JSONObject(s!!)
            if (jsonObject.has("message"))
                baseActivity.showToast(s)
        } catch (e: JSONException) {
            baseActivity.showToast(s!!)
        }
    }

    private fun loginValidation(): Boolean {
        var valid = true
        when {
            email.get().isNullOrEmpty() -> {
                valid = false
                validateMessage.postValue("Please provide username")
            }
            !UtilFile.emailValid(email.get()!!) -> {
                valid = false
                validateMessage.postValue("Please Enter Valid Email")
            }
            password.get().isNullOrEmpty() -> {
                valid = false
                validateMessage.postValue("Please provide password")
            }
            password.get()!!.trim().length < 8 -> {
                valid = false
                validateMessage.postValue("Password Should Be Minimum Of 8 Characters")
            }
        }
        return valid
    }
}