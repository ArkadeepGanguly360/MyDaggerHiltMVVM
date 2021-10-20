package com.development.mydaggerhiltmvvm.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("isLoggedIn") val isLoggedIn: Boolean,
    @SerializedName("token") val token: String,
    @SerializedName("data") val data: UserData,
    @SerializedName("message") val message: String
)
