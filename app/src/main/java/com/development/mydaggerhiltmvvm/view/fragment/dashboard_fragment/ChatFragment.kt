package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.ChatedUserAdapter
import com.development.mydaggerhiltmvvm.adapter.MyFriendListAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentChatBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.FriendsResponse
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFragment : BaseFragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var dashboardViewModel: DashboardViewModel
    var chatedUserList = ArrayList<FriendsData>()
    private lateinit var chatedUserAdapter: ChatedUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_chat, layoutInflater, container)
        dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        binding.viewModel = dashboardViewModel
        initChatedUserAdapter()
        CoroutineScope(Dispatchers.Main).launch {
            dashboardViewModel.getMyFriendList()
        }

        observeFriendList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initChatedUserAdapter() {
        chatedUserAdapter = ChatedUserAdapter(
            requireActivity(),
            chatedUserList,
            object : RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {
                    /*val bundle = Bundle()
                    bundle.putSerializable("FriendDetails", chatedUserList[position])
                    goToNextFragment(
                        R.id.action_dashboardFragment_to_friendDetailsFragment,
                        bundle
                    )*/
                }
            })

        binding.recycleChatedUserList.apply {
            adapter = chatedUserAdapter
        }
    }

    private fun observeFriendList() {
        CoroutineScope(Dispatchers.Main).launch {
            var observer = Observer<FriendsResponse> { response ->
                response.let {
                    // showToast(response.message)
                    chatedUserList.clear()
                    chatedUserList.addAll(response.data)
                    chatedUserAdapter.notifyDataSetChanged()
                }
            }
            dashboardViewModel.observeFriendList().observe(viewLifecycleOwner, observer)
        }
    }
}