package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentDashboardBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentFriendDetailsBinding
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FriendDetailsFragment : BaseFragment() {

    private val TAG = FriendDetailsFragment::class.java.simpleName
    private lateinit var binding: FragmentFriendDetailsBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    var friendDetails :FriendsData?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            friendDetails = it.getSerializable("FriendDetails") as FriendsData?
            Log.e(TAG, "List - $friendDetails")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = putContentView(R.layout.fragment_friend_details, layoutInflater, container)
            dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
            binding.viewModel = dashboardViewModel
        }
        return binding.root
    }
}