package com.development.mydaggerhiltmvvm.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FriendDetails(
    @SerializedName("_id") val _id : String,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("user_name") val user_name : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("full_name") val full_name : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("profile_image") val profile_image : String,
    @SerializedName("deviceToken") val deviceToken : String,
    @SerializedName("birth_date") val birth_date : String,
    @SerializedName("location") val location : String,
    @SerializedName("settings_friend_request") val settings_friend_request : String,
    @SerializedName("settings_message_request") val settings_message_request : String,
    @SerializedName("otp") val otp : String,
    @SerializedName("code") val code : String,
    @SerializedName("social_id") val social_id : String,
    @SerializedName("isEmailVerified") val isEmailVerified : Boolean,
    @SerializedName("isPhoneVerified") val isPhoneVerified : Boolean,
    @SerializedName("deviceType") val deviceType : String,
    @SerializedName("register_type") val register_type : String,
    @SerializedName("isDeleted") val isDeleted : Boolean,
    @SerializedName("isActive") val isActive : Boolean,
    @SerializedName("role") val role : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String
)
