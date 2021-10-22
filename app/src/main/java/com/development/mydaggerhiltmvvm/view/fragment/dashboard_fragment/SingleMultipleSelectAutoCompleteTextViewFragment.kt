package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProvider
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.autoCompleteAdapter.FriendForMultiSelectionAutoCompletetTextViewAdapter
import com.development.mydaggerhiltmvvm.adapter.autoCompleteAdapter.FriendForSingleSelectionAutoCompletetTextViewAdapter
import com.development.mydaggerhiltmvvm.adapter.autoCompleteAdapter.SearchFriendAutoCompleteAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentDashboardBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentSingleMultipleSelectAutoCompleteTextViewBinding
import com.development.mydaggerhiltmvvm.interfaces.OnCheckedAutoCompleteItemClicked
import com.development.mydaggerhiltmvvm.model.MyFriendSData
import com.development.mydaggerhiltmvvm.view.activity.dashboard_activity.DashboardViewModel
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class SingleMultipleSelectAutoCompleteTextViewFragment : BaseFragment() {

    private lateinit var binding: FragmentSingleMultipleSelectAutoCompleteTextViewBinding

    var friendList = ArrayList<MyFriendSData>()

    /*Todo For Multi Selection*/
    var selectedFriends = ObservableField("")
    var selectedFriendList = ArrayList<MyFriendSData>()
    private lateinit var friendAutoCompletetTextViewAdapter: FriendForMultiSelectionAutoCompletetTextViewAdapter

    /*Todo For Search*/
    var searchedSelectedFriends = ObservableField("")
    private lateinit var searchFriendAutoCompleteAdapter: SearchFriendAutoCompleteAdapter

    /*Todo For Single Selection*/
    var selectedSingleFriends = ObservableField("")
    private lateinit var friendForSingleSelectionAutoCompletetTextViewAdapter: FriendForSingleSelectionAutoCompletetTextViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_single_multiple_select_auto_complete_text_view, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFriendList()
        initMultiSelectFriendAdapter()
        initSearchFriendAdapter()
        initSingleSelectFriendAdapter()
    }

    private fun getFriendList() {
        friendList.add(
            MyFriendSData(
                "1",
                "Arkadeep"
            )
        )
        friendList.add(
            MyFriendSData(
                "2",
                "Dipanwita"
            )
        )
        friendList.add(
            MyFriendSData(
                "3",
                "Atrijit"
            )
        )
        friendList.add(
            MyFriendSData(
                "4",
                "Rajat"
            )
        )
        friendList.add(
            MyFriendSData(
                "5",
                "Arghya"
            )
        )
    }


    /*Todo For Multi Selection*/
    private fun initMultiSelectFriendAdapter() {
        friendAutoCompletetTextViewAdapter = FriendForMultiSelectionAutoCompletetTextViewAdapter(
            requireActivity(),
            R.layout.autocomplete_view_list_item,
            friendList,
            object : OnCheckedAutoCompleteItemClicked {
                override fun onIemClicked(position: Int?, icChecked: Boolean?) {
                    if (icChecked!!)
                        selectedFriendList.add(friendList.get(position!!))
                    else
                        selectedFriendList.remove(friendList.get(position!!))

                    selectedFriends.set("")

                    for (i in selectedFriendList) {
                        if (selectedFriends.get() != "") {
                            selectedFriends.set(selectedFriends.get() + ", " + i.name)
                        } else {
                            selectedFriends.set(selectedFriends.get() + " " + i.name)
                        }
                    }
                }
            })

        binding.tvAutoFriend.dropDownVerticalOffset = 20
        binding.tvAutoFriend.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.rounded_less_corner_white_bg
            )
        )
        binding.tvAutoFriend?.setAdapter(friendAutoCompletetTextViewAdapter)
        binding.tvAutoFriend?.setOnTouchListener { v, event ->
            if ((v as MaterialAutoCompleteTextView).text.toString() != "")
                friendAutoCompletetTextViewAdapter?.filter?.filter(null)
            binding.tvAutoFriend?.showDropDown()
            false
        }
    }

    /*Todo For Single Selection*/
    private fun initSingleSelectFriendAdapter() {
        friendForSingleSelectionAutoCompletetTextViewAdapter = FriendForSingleSelectionAutoCompletetTextViewAdapter(
            requireActivity(),
            R.layout.autocomplete_new_view_list_item,
            friendList,
            object : OnCheckedAutoCompleteItemClicked {
                override fun onIemClicked(position: Int?, icChecked: Boolean?) {
                    selectedSingleFriends.set(friendList.get(position!!).name)
                    binding.tvSingleAutoFriend.dismissDropDown()
                }
            })

        binding.tvSingleAutoFriend.dropDownVerticalOffset = 20
        binding.tvSingleAutoFriend.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.rounded_less_corner_white_bg
            )
        )
        binding.tvSingleAutoFriend?.setAdapter(friendForSingleSelectionAutoCompletetTextViewAdapter)
        binding.tvSingleAutoFriend?.setOnTouchListener { v, event ->
            if ((v as MaterialAutoCompleteTextView).text.toString() != "")
                friendForSingleSelectionAutoCompletetTextViewAdapter?.filter?.filter(null)
            binding.tvSingleAutoFriend?.showDropDown()
            false
        }
    }

    /*Todo For Search*/
    private fun initSearchFriendAdapter() {
        searchFriendAutoCompleteAdapter =
            SearchFriendAutoCompleteAdapter(
                requireActivity(),
                R.layout.search_autocomplete_view_list_item,
                friendList
            )

        binding.tvSearchAutoFriend.dropDownVerticalOffset = 20
        binding.tvSearchAutoFriend.setDropDownBackgroundDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                R.drawable.rounded_less_corner_white_bg
            )
        )
        binding.tvSearchAutoFriend?.setAdapter(searchFriendAutoCompleteAdapter)
        binding.tvSearchAutoFriend.onClickAutoCompleteItem { it, position ->
            val data = it as MyFriendSData
            searchedSelectedFriends.set(data.name)
            baseActivity.hideKeyBoard(binding.tvSearchAutoFriend)
        }
    }

    /*Todo For Search*/
    private fun AppCompatAutoCompleteTextView.onClickAutoCompleteItem(listener: (Any?, Int) -> Unit) {
        this.setOnItemClickListener { parent: AdapterView<*>?,
                                      view: View?,
                                      position: Int,
                                      id: Long ->

            val item = parent?.getItemAtPosition(position)

            when (item) {
                is MyFriendSData -> {
                    this.setText(item.name)
                }

            }
            listener(item, position)
            this.setSelection(this.text.length)
        }
    }
}