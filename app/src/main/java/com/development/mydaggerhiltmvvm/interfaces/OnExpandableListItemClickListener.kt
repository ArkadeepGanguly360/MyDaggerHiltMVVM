package com.development.mydaggerhiltmvvm.interfaces

import android.view.View

interface OnExpandableListItemClickListener {
    fun onIemClicked(view: View?, parentPosition: Int?, childPosition: Int?)
}