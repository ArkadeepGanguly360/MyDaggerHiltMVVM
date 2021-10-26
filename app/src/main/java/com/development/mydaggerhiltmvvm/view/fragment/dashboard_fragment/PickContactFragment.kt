package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentPickContactBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentPictureInPictureBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_pick_contact.*

class PickContactFragment : BaseFragment() {

    private lateinit var binding: FragmentPickContactBinding

    //Todo contact permission code
    private val CONTACT_PERMISSION_CODE = 1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_pick_contact, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Todo handle click, to pick contact
        addFab.setOnClickListener {
            //Todo check permission allowed or not
            if (checkContactPermission()) {
                //Todo allowed
                pickContact()
            } else {
                //Todo not allowed, request
                requestContactPermission()
            }
        }
    }

    private fun checkContactPermission(): Boolean {
        //Todo check if permission was granted/allowed or not, returns true if granted/allowed, false if not
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission() {
        //Todo request the READ_CONTACTS permission
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)
    }

    private fun pickContact() {
        //Todo intent ti pick contact
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        resultLauncher.launch(intent)
        //startActivityForResult(intent, CONTACT_PICK_CODE)
    }


    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            contactTv.text = ""
            val cursor1: Cursor
            val cursor2: Cursor?

            //Todo get data from intent
            val data: Intent? = result.data
            val uri = data!!.data

            cursor1 = requireActivity().contentResolver.query(uri!!, null, null, null, null)!!
            if (cursor1.moveToFirst()) {

                //Todo get contact details
                val contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID))
                val contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val contactThumbnail = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
                val idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                val idResultHold = idResults.toInt()

                //Todo set details: contact id, contact name, image
                contactTv.append("ID: $contactId")
                contactTv.append("\nName: $contactName")

                //Todo set image, first check if uri/thumbnail is not null
                if (contactThumbnail != null) {
                    thumbnailIv.setImageURI(Uri.parse(contactThumbnail))
                } else {
                    thumbnailIv.setImageResource(R.drawable.ic_profile)
                }

                //Todo check if contact has a phone number or not
                if (idResultHold == 1) {
                    cursor2 = requireActivity().contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null
                    )

                    //Todo a contact may have multiple phone numbers
                    while (cursor2!!.moveToNext()) {

                        //Todo get phone number
                        val contactNumber = cursor2.getString(cursor2.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER))

                        //Todo set phone number
                        contactTv.append("\nPhone: $contactNumber")
                    }
                    cursor2.close()
                }
                cursor1.close()
            }
        } else {
            //Todo cancelled picking contact
            Toast.makeText(requireActivity(), "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Todo handle permission request results || calls when user from Permission request dialog presses Allow or Deny
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Todo permission granted, can pick contact
                pickContact()
            } else {
                //Todo permission denied, cann't pick contact, just show message
                Toast.makeText(requireActivity(), "Permission denied...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}