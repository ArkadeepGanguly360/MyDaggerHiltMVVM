package com.development.mydaggerhiltmvvm.view.fragment.base_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragmentAbstract : Fragment() {

    abstract fun showToast(msg : String)

    abstract fun showSnackBar(v: View, msg : String)

    abstract fun goToNextFragment(destinationId : Int,bundle: Bundle?)

    abstract fun <T : ViewDataBinding?> putContentView(@LayoutRes resId: Int, inflater: LayoutInflater, container: ViewGroup?): T

}