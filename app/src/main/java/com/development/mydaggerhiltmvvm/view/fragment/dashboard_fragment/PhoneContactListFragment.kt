package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.development.mydaggerhiltmvvm.PhoneContactListAdapter
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.MyFriendListAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentPhoneContactListBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentPickContactBinding
import com.development.mydaggerhiltmvvm.interfaces.RecyclerViewItemOnClickListener
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.PhoneContactModel
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class PhoneContactListFragment : BaseFragment() {

    private lateinit var binding: FragmentPhoneContactListBinding
    var contactList = ArrayList<PhoneContactModel>()
    private lateinit var phoneContactListAdapter: PhoneContactListAdapter

    //Todo kotlin permission
    private var listPermission = java.util.ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listPermission.addAll(MyConstant.APP_PERMISSION.READ_CONTACT_PERMISSION)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_phone_contact_list, layoutInflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissionToReadContacts()
        initContactAdapter()
    }

    private fun checkPermissionToReadContacts() {
        if (baseActivity.checkPermission(listPermission))
            getAllPhoneContacts()
        else {
            baseActivity.askPermission(listPermission) {
                if (it)
                    getAllPhoneContacts()
            }
        }
    }


    private fun initContactAdapter() {
        phoneContactListAdapter = PhoneContactListAdapter(
            requireActivity(),
            contactList,
            object : RecyclerViewItemOnClickListener {
                override fun onViewClick(position: Int) {

                }
            })

        binding.rvContactlist.apply {
            adapter = phoneContactListAdapter
        }
    }

    private fun getAllPhoneContacts() {
        val phones = requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val photo = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI))

            val contactModel = PhoneContactModel(name,phoneNumber,photo)
            contactList!!.add(contactModel)
            Log.d("name>>", name + "  " + phoneNumber)
        }
        phones.close()
    }
}