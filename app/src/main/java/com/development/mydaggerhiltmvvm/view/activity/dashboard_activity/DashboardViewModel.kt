package com.development.mydaggerhiltmvvm.view.activity.dashboard_activity

import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingSource
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.interfaces.WebInterface
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.FriendsResponse
import com.development.mydaggerhiltmvvm.restService.AllWebServiceCall
import com.development.mydaggerhiltmvvm.restService.RestInterface
import com.development.mydaggerhiltmvvm.roomDb.RoomRepository
import com.development.mydaggerhiltmvvm.util.NetworkHelper
import com.development.mydaggerhiltmvvm.util.UserSharedPrefrence
import com.development.mydaggerhiltmvvm.util.common_utils.UtilFile.emailValid
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.google.gson.Gson
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject

class DashboardViewModel @ViewModelInject constructor(
    private var restInterface: RestInterface,
    private var allWebServiceCall: AllWebServiceCall,
    private val networkHelper: NetworkHelper,
    private val userPref: UserSharedPrefrence
   /* private val repository: RoomRepository*/
) : ViewModel(), WebInterface {

    private val TAG = DashboardViewModel::class.java.simpleName
    private lateinit var baseActivity: BaseActivity
    private lateinit var dashboardActivity: DashboardActivity

    /*private var validateMessage = MutableLiveData<String>()
    private var networkStatus = MutableLiveData<String>()*/
    var progressbarVisibility = ObservableField(View.GONE)

    /*Todo For Profile*/
    var name = ObservableField("")
    var phoneNo = ObservableField("")
    var email = ObservableField("")
    var image = ObservableField("")
    var placeHolderImage = ObservableField(R.drawable.user_profile)
    var profileImage: MultipartBody.Part? = null

    /*Todo For Dashboard*/
    private var friendResponse = MutableLiveData<FriendsResponse>()

    var currentPage =  MutableLiveData(1)
    var totalPageCount = 0

    fun initActivity(baseActivity: BaseActivity, dashboardActivity: DashboardActivity) {
        this.baseActivity = baseActivity
        this.dashboardActivity = dashboardActivity
    }

 /*   fun initializeDB(context: Context) : MainDatabase {
        return MainDatabase.getDataseClient(context)
    }*/

    /*  fun observeValidateMsg(): MutableLiveData<String> {
          return validateMessage
      }

      fun observeNetworkStatus(): MutableLiveData<String> {
          return networkStatus
      }
  */

    fun observeFriendList(): MutableLiveData<FriendsResponse> {
        return friendResponse
    }

   /* fun getAllRecords(): MutableLiveData<List<FriendsData>> {
        return repository.getAllRecords()
    }*/

    suspend fun getMyFriendList() {
        if (networkHelper.isNetworkConnected()) {
            //progressbarVisibility.set(View.VISIBLE)
            baseActivity.showProgressDialog()

          /*  val map = HashMap<String, Any>()
            map["page_no"] = currentPage.value!!
            map["page_limit"] = 10*/

            val query = restInterface.getMyFriendList(userPref.getUserToken()/*,map*/)
            allWebServiceCall.webServiceCall(query, this, "getMyFriendList")
        } else {
            //networkStatus.postValue("No Internet Connection")
            baseActivity.showToast("No Internet Connection")
        }
    }

    suspend fun sendProfileUpdateData() {
        if (updateProfileValidation()) {
            if (networkHelper.isNetworkConnected()) {
                //progressbarVisibility.set(View.VISIBLE)
                baseActivity.showProgressDialog()
                val query = restInterface.userUpdateProfile(
                    userPref.getUserToken(),
                    baseActivity.createPartFromString(email.get()!!.trim()),
                    baseActivity.createPartFromString(phoneNo.get()!!.trim()),
                    profileImage,
                    baseActivity.createPartFromString(name.get()!!.trim())
                )
                allWebServiceCall.webServiceCall(query, this, "updateProfile")
            } else {
                // networkStatus.postValue("No Internet Connection")
                baseActivity.showToast("No Internet Connection")
            }
        }
    }

    override fun <E> resultSuccess(t: E, method_name: String?) {
        baseActivity.cancelProgressDialog()
        try {
            when (method_name) {
                "updateProfile" -> {
                    val jsonObject = JSONObject(t.toString())
                    baseActivity.showToast(jsonObject.optString("message"))
                }
                "getMyFriendList" -> {
                    val response = Gson().fromJson(t.toString(), FriendsResponse::class.java)
                    friendResponse.postValue(response)
                 /*   response.data?.forEach {
                        repository.insertRecord(it)
                    }*/
                }
            }
        } catch (e: JSONException) {
        } finally {
            // progressbarVisibility.set(View.GONE)
            baseActivity.cancelProgressDialog()
        }
    }

    override fun failureSuccess(s: String?) {
        //progressbarVisibility.set(View.GONE)
        baseActivity.cancelProgressDialog()
        try {
            val jsonObject = JSONObject(s!!)
            if (jsonObject.has("message"))
                baseActivity.showToast(s)
        } catch (e: JSONException) {
            baseActivity.showToast(s!!)
        }
    }

    private fun updateProfileValidation(): Boolean {
        var valid = true
        when {
            name.get().isNullOrEmpty() -> {
                valid = false
                // validateMessage.postValue("Please Enter Full Name")
                baseActivity.showToast("Please Enter Full Name")
            }
            phoneNo.get().isNullOrEmpty() -> {
                valid = false
                // validateMessage.postValue("Please Enter Phone Number")
                baseActivity.showToast("Please Enter Phone Number")
            }
            phoneNo.get()!!.length < 10 -> {
                valid = false
                //validateMessage.postValue("Please Enter Phone Number")
                baseActivity.showToast("Please Enter Phone Number")
            }
            phoneNo.get()!!.length > 12 -> {
                valid = false
                //validateMessage.postValue("Please Enter Phone Number")
                baseActivity.showToast("Please Enter Phone Number")
            }
            email.get().isNullOrEmpty() -> {
                valid = false
                //validateMessage.postValue("Please Enter Email")
                baseActivity.showToast("Please Enter Email")
            }
            !emailValid(email.get()!!) -> {
                valid = false
                //validateMessage.postValue("Please Enter Valid Email")
                baseActivity.showToast("Please Enter Valid Email")
            }
        }
        return valid
    }
}

