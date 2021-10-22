package com.development.mydaggerhiltmvvm.util

import android.Manifest

class MyConstant {

    object COMMON_CONST {
        const val DEVICE_TYPE = "android"
        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"


        private const val BASE_URL = "https://heavenplusone-api.dedicateddevelopers.us/"             // development URL
        const val BASE_URL_API = BASE_URL + "api/"                      // development URL
        const val PROFILE_IMG_PATH = BASE_URL + "uploads/user/" // User Profile Image
        const val MEMORIAL_IMAGE_VIDEO_PATH = BASE_URL + "uploads/memorial/"  // Memorial Image or Video Path
        const val MEMORIAL_PROFILE_IMAGE_PATH = BASE_URL + "uploads/memorial/profile_img/"  // Memorial Profile Image
        const val REACTION_IMAGE_PATH = BASE_URL + "uploads/reaction/"
    }
    object FEED_TYPE {
        const val GIFTS = "gifts"
        const val COMMENTS = "comments"
        const val MESSAGES = "messages"
    }

    object FRIEND_REQUEST_TYPE {
        const val ACCEPT = "accept"
        const val DENY = "deny"
    }

    object MY_MEMORIALS {
        const val MEMORIAL_PROFILE = "memorial_profile"
        const val MEMORIAL_EDIT = "memorial_edit"
    }

    object APP_PERMISSION {

        val ABOVE_API_29_PERMISSION = listOf(
            Manifest.permission.CAMERA
        )

        val ACCESS_FINE_LOCATION = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val ALL_PERMISSION = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    object IMAGE_PICK_TYPE {
        const val CAMERA = "Camera"
        const val GALLERY = "Gallery"
    }

    object DATE_FORMAT {
        const val GENERAL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DAY_DATE_MONTH_YEAR_FORMAT = "EEEE, d'th' MMMM yyyy"
        const val MONTH_DATE_YEAR = "MM/dd/yyyy"
        const val DAT_MONTH_YEAR = "dd/MM/yyyy"
        const val YEAR_MONTH_DATE = "yyyy-MM-dd"
        const val DATE_MONTH_YEAR = "dd-MM-yyyy"
        const val MONTH_DAY = "MM/dd/yy"
        const val MOTH_DATE_YEAR = "MMM dd, YYYY"
        const val DATE_TIME_AM_PM = YEAR_MONTH_DATE + "_hhaa"
        const val TIME_AM_PM = "hh:mm aa"
        const val DATE_TH_MONTH_YEAR = "d'th' MMM,yyyy"
        const val ONLY_YEAR = "yyyy"
        const val CHAT_SHOW_TIME = "MM/dd/yy,$TIME_AM_PM"
    }

    object NOTIFICATION_TYPE {
        const val NOTIFY_TYPE = "NOTIFY_TYPE"
        const val NOTIFICATION_TYPE = "type"
        const val GENERAL = "GENERAL"
        const val PUSH = "PUSH"
        const val MATCH = "MATCH"
        const val SUPER_LIKE = "SUPER-LIKE"
        const val TAG = "TAG"
        const val PROMOTION = "PROMOTION"
        const val LIVE_EVENT = "LIVE EVENT"
        const val MESSAGE = "message"
        const val BROADCASTING = "Broadcasting"
    }
}