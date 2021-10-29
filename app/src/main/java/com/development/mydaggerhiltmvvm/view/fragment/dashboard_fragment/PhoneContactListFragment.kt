package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.PhoneContactListAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentPhoneContactListBinding
import com.development.mydaggerhiltmvvm.interfaces.OnCheckedAutoCompleteItemClicked
import com.development.mydaggerhiltmvvm.model.PhoneContactModel
import com.development.mydaggerhiltmvvm.util.MyConstant
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment

class PhoneContactListFragment  : BaseFragment() {

    private lateinit var binding: FragmentPhoneContactListBinding
    var contactList = ArrayList<PhoneContactModel>()
    private lateinit var phoneContactListAdapter: PhoneContactListAdapter

    var list = ArrayList<String>()

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
            object : OnCheckedAutoCompleteItemClicked {
                override fun onIemClicked(position: Int?, icChecked: Boolean?) {
                    if(icChecked!!){
                        list.add(contactList[position!!].name!!)
                    }
                    else {
                        list.remove(contactList[position!!].name!!)
                    }

                    Log.e("Contact","ContactList : $list")
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
        }
        phones.close()
    }
}