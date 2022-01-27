package com.development.mydaggerhiltmvvm.model

import android.graphics.drawable.Drawable

data class WhatsappUser(
    val userImage: Drawable,
    val userName: String,
    val allSeen: Boolean,
    val statusList: List<WhatsappStatus>
)
