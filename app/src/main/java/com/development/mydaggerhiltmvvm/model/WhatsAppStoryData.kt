package com.development.mydaggerhiltmvvm.model

data class WhatsAppStoryData(
    val mediaUrl: String,
    val mimeType: String,
    val allSeen: Boolean,
    val statusList: List<WhatsappStatus>
)
