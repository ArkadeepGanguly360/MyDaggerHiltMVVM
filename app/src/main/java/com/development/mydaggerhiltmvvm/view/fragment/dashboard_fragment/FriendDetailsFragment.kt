package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.ProductTypeSpinnerArrayAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentFriendDetailsBinding
import com.development.mydaggerhiltmvvm.model.FriendsData
import com.development.mydaggerhiltmvvm.model.ProductCategory
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_friend_details.*

class FriendDetailsFragment : BaseFragment(), AdapterView.OnItemSelectedListener {

    private val TAG = FriendDetailsFragment::class.java.simpleName
    private lateinit var binding: FragmentFriendDetailsBinding

    var friendDetails :FriendsData?=null

    var languages = arrayOf(
        "English",
        "French",
        "Spanish",
        "Hindi",
        "Russian",
        "Telugu",
        "Chinese",
        "German",
        "Portuguese",
        "Arabic",
        "Dutch",
        "Urdu",
        "Italian",
        "Tamil",
        "Persian",
        "Turkish",
        "Other"
    )
    var productCategoryList = ArrayList<ProductCategory>()
    private var productTypeSpinnerAdapter: ProductTypeSpinnerArrayAdapter? = null

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
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aa = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, languages)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_spinner!!.adapter = aa

     /*   productTypeSpinnerAdapter = ProductTypeSpinnerArrayAdapter(requireActivity(), productCategoryList!!)
        binding.spSpinner.adapter = productTypeSpinnerAdapter

        productCategoryList.add(
            ProductCategory(
                "Dipanwita","0"
            )
        )
        productCategoryList.add(
            ProductCategory(
                "Nilam","1"
            )
        )
        productCategoryList.add(
            ProductCategory(
                "Tapasree","2"
            )
        )
        productCategoryList.add(
            ProductCategory(
                "Poulami","3"
            )
        )*/

        sp_spinner!!.onItemSelectedListener = this
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        tv!!.text = "Selected : "+languages[position]
       // tv.text = "Selected : "+ productCategoryList!![position].name
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }
}