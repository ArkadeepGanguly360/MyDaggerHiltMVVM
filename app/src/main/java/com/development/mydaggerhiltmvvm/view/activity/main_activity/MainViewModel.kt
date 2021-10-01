package com.development.mydaggerhiltmvvm.view.activity.main_activity

import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.development.mydaggerhiltmvvm.interfaces.WebInterface
import com.development.mydaggerhiltmvvm.model.FriendsResponse
import com.development.mydaggerhiltmvvm.restService.AllWebServiceCall
import com.development.mydaggerhiltmvvm.restService.RestInterface
import com.development.mydaggerhiltmvvm.util.NetworkHelper
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class MainViewModel @ViewModelInject constructor(
    private var restInterface: RestInterface,
    private var allWebServiceCall: AllWebServiceCall,
    private val networkHelper: NetworkHelper) : ViewModel(), WebInterface {

    var progressbarVisibility = ObservableField(View.GONE)
    private var friendResponse = MutableLiveData<FriendsResponse>()
    private var failureMessage = MutableLiveData<String>()
    private var networkStatus = MutableLiveData<String>()

    fun observeFailureMsg(): MutableLiveData<String> {
        return failureMessage
    }

    fun observeNetworkStatus(): MutableLiveData<String> {
        return networkStatus
    }

    fun observeDetails(): MutableLiveData<FriendsResponse> {
        return friendResponse
    }

    suspend fun getMyFriendList() {
        if (networkHelper.isNetworkConnected()) {
            progressbarVisibility.set(View.VISIBLE)
            val query =
                restInterface.getMyFriendList("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYxMDExZmIzZWQ5MzZjMDZhZjM0ZWQ3MSIsInJvbGVfaWQiOiI2MDQyMzFkNjAyNjQyNGRhNzMxMmMxNDciLCJpYXQiOjE2MzMwMDM0MzcsImV4cCI6MTYzNTU5NTQzN30.8KW63bUHbw_Sp9c824rsqy878FVCuTxJpLF2h733EL0")
            allWebServiceCall.webServiceCall(query, this, "getMyFriendList")
        } else {
            networkStatus.postValue("No Internet Connection")
        }
    }

    override fun <E> resultSuccess(t: E, method_name: String?) {
        try {
            when (method_name) {
                "getMyFriendList" -> {
                    val response = Gson().fromJson(t.toString(), FriendsResponse::class.java)
                    friendResponse.postValue(response)
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
                failureMessage.postValue(s)
        } catch (e: JSONException) {
            failureMessage.postValue(s)
        }
    }
}