package com.development.mydaggerhiltmvvm.view.activity.main_activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.MyFriendListAdapter
import com.development.mydaggerhiltmvvm.databinding.ActivityMainBinding
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.FriendsResponse
import com.development.mydaggerhiltmvvm.view.activity.base_activity.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    var friendList = ArrayList<FriendsData>()
    private lateinit var myFriendListAdapter: MyFriendListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        GlobalScope.launch {
            viewModel.getMyFriendList()
        }

        initMyFriendListAdapter()
        getNetworkStatus()
        getFailureMsg()
        getDetails()
    }

    private fun initMyFriendListAdapter() {
        myFriendListAdapter = MyFriendListAdapter(
            this,
            friendList
        )

        binding.recycleFrnd.apply {
            adapter = myFriendListAdapter
        }
    }

    private fun getNetworkStatus(){
        var observer = Observer<String> { response ->
            showToast(response)
        }
        viewModel.observeNetworkStatus().observe(this, observer)
    }


    private fun getFailureMsg(){
        var observer = Observer<String> { response ->
            showToast(response)
        }
        viewModel.observeFailureMsg().observe(this, observer)
    }

    private fun getDetails() {
        var observer = Observer<FriendsResponse> { response ->
            showToast(response.message)
            if (response != null) {
                friendList.clear()
                friendList.addAll(response.data)
                myFriendListAdapter.notifyDataSetChanged()
            }
        }
        viewModel.observeDetails().observe(this, observer)
    }
}