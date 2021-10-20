package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.MyFriendListAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentDashboardBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentProfileBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.FriendsResponse
import com.development.mydaggerhiltmvvm.util.common_utils.UtilExtensions
import com.development.mydaggerhiltmvvm.util.common_utils.UtilExtensions.myToast
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : BaseFragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var dashboardViewModel: DashboardViewModel

    var friendList = ArrayList<FriendsData>()
    private lateinit var myFriendListAdapter: MyFriendListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = putContentView(R.layout.fragment_dashboard, layoutInflater, container)
            dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
            binding.viewModel = dashboardViewModel

            initMyFriendListAdapter()

            CoroutineScope(Dispatchers.Main).launch {
                dashboardViewModel.getMyFriendList()
            }

            observeFriendList()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* observeNetworkStatus()
        observeValidateMsg()*/
    }

    private fun initMyFriendListAdapter() {
        myFriendListAdapter = MyFriendListAdapter(
            requireActivity(),
            friendList,
            object : RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putSerializable("FriendDetails", friendList[position])
                    goToNextFragment(
                        R.id.action_dashboardFragment_to_friendDetailsFragment,
                        bundle
                    )
                }
            })

        binding.recycleFrnd.apply {
            adapter = myFriendListAdapter
        }
    }

    private fun observeFriendList() {
        CoroutineScope(Dispatchers.Main).launch {
            var observer = Observer<FriendsResponse> { response ->
                response.let {
                    showToast(response.message)
                    friendList.clear()
                    friendList.addAll(response.data)
                    myFriendListAdapter.notifyDataSetChanged()
                }
            }
            dashboardViewModel.observeFriendList().observe(viewLifecycleOwner, observer)
        }
    }

    /*  private fun observeNetworkStatus() {
       var observer = Observer<String> { response ->
           showToast(response)
       }
       dashboardViewModel.observeNetworkStatus().observe(viewLifecycleOwner, observer)
   }

   private fun observeValidateMsg() {
       var observer = Observer<String> { response ->
           showToast(response)
       }
       dashboardViewModel.observeValidateMsg().observe(viewLifecycleOwner, observer)
   }*/
}