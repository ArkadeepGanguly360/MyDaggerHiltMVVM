package com.development.mydaggerhiltmvvm.interfaces

import com.development.mydaggerhiltmvvm.util.whatsappCircularStatus.CircularStatusView

interface CircularStatusViewOnClickListener {
    fun onIemClicked(view: CircularStatusView?, position: Int?)
}