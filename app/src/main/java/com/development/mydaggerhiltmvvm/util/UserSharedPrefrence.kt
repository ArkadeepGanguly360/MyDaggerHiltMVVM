package com.development.mydaggerhiltmvvm.util

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.util.Log
import com.development.mydaggerhiltmvvm.model.UserData
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSharedPrefrence @Inject constructor(@ApplicationContext private val context: Context) {

    private val prefName = "UserPref"
    private val MODE = 0
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val USER_TOKEN = "user_token"
    private val USER_DETAILS = "user_details"
    private val USER_LOGIN = "user_login"
    private val USER_NAME = "user_name"
    private val USER_PASSWORD = "user_password"

    private val CURRENT_LAT = "current_lat"
    private val CURRENT_LONG = "current_long"
    private val CURRENT_ADDRESS = "current_address"
    private val UPDATED_EMAIL = "updated_email"

    private val VIDEOS = "videos"
    private val MEMORIALID = "memorialid"
    private val TYPE = "type"


    init {
        pref = context.getSharedPreferences(prefName, MODE)
        editor = pref.edit()
        editor.apply()
    }

    companion object {
        private var instance: UserSharedPrefrence? = null

        fun getInstance(context: Context): UserSharedPrefrence {
            if (instance == null)
                instance = UserSharedPrefrence(context)

            return instance!!
        }
    }

    fun setUserToken(fullName: String) {
        editor.putString(USER_TOKEN, fullName)
        editor.commit()
    }

    fun getUserToken(): String {
        return pref.getString(USER_TOKEN, "")!!
    }


    fun setUserName(userName: String) {
        editor.putString(USER_NAME, userName)
        editor.commit()
    }

    fun getUserName(): String {
        return pref.getString(USER_NAME, "")!!
    }

    fun setUpdatedEmail(updatedEmail: String) {
        editor.putString(UPDATED_EMAIL, updatedEmail)
        editor.commit()
    }

    fun getUpdatedEmail(): String {
        return pref.getString(UPDATED_EMAIL, "")!!
    }


    fun saveUserData(s: String) {
        editor.putString(USER_DETAILS, s)
        editor.commit()
    }

    fun getUserData(): UserData? {
        val details = pref.getString(USER_DETAILS, "")
        Log.d("User details", details!!)
        return if (details?.isNotEmpty()!!)
            Gson().fromJson(details, UserData::class.java)
        else
            null
    }


    fun setUserLogin(value: Boolean) {
        editor.putBoolean(USER_LOGIN, value)
        editor.apply()
    }

    fun isUserLogin(): Boolean {
        return pref.getBoolean(USER_LOGIN, false)!!
    }

    fun setUserCurrentLocation(location: Location) {
        val latitude = location.latitude
        val longitude = location.longitude
        editor.putString(CURRENT_LAT, "" + latitude)
        editor.putString(CURRENT_LONG, "" + longitude)
    }

    fun getUserCurrentLocation(): LatLng? {
        val lati = pref.getString(CURRENT_LAT, "0.0")
        val longi = pref.getString(CURRENT_LONG, "0.0")
        return LatLng(
            Objects.requireNonNull(lati)!!.toDouble(), Objects.requireNonNull(longi)!!.toDouble()
        )
    }

    fun getCURRENT_ADDRESS(): String? {
        return pref.getString(CURRENT_ADDRESS, "")
    }

    fun setCURRENT_ADDRESS(address: String?) {
        editor.putString(CURRENT_ADDRESS, address)
        editor.apply()
    }

    fun setVideos(videos: String) {
        editor.putString(VIDEOS, videos)
        editor.commit()
    }

    fun getVideos(): String {
        return pref.getString(VIDEOS, "")!!
    }

    fun setMemorialId(memorialId: String) {
        editor.putString(MEMORIALID, memorialId)
        editor.commit()
    }

    fun getType(): String {
        return pref.getString(TYPE, "")!!
    }

    fun setType(type: String) {
        editor.putString(TYPE, type)
        editor.commit()
    }

    fun getMemorialId(): String {
        return pref.getString(MEMORIALID, "")!!
    }

    fun clearPref() {
        editor.clear()
        editor.commit()
    }
}