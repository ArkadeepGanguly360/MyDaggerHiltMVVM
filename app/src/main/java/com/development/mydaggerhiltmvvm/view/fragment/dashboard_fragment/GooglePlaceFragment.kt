package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.adapter.PlaceAutoCompleteAdapter
import com.development.mydaggerhiltmvvm.databinding.FragmentGooglePlaceBinding
import com.development.mydaggerhiltmvvm.databinding.FragmentReadMoreTextviewBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import java.io.IOException
import java.util.*

class GooglePlaceFragment : BaseFragment(), PlaceAutoCompleteAdapter.ClickListener {

    private lateinit var binding: FragmentGooglePlaceBinding
    var address = ObservableField("")
    var placeListVisibility = ObservableField(View.GONE)
    private lateinit var placeAutoCompleteAdapter: PlaceAutoCompleteAdapter
    var State = ""
    var City = ""

    private val callback: Observable.OnPropertyChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable, propertyId: Int) {
            if (sender === address) {
                val s: String = address.get().toString()
                if (!s.length.equals("")) {
                    placeAutoCompleteAdapter.getFilter().filter(s)
                    if (placeListVisibility.get() == View.GONE) {
                        placeListVisibility.set(View.VISIBLE)
                    }
                } else {
                    if (placeListVisibility.get() == View.VISIBLE) {
                        placeListVisibility.set(View.GONE)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = putContentView(R.layout.fragment_google_place, layoutInflater, container)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        address.addOnPropertyChangedCallback(callback)
        placeAutoCompleteAdapter = PlaceAutoCompleteAdapter(requireActivity())
        placeAutoCompleteAdapter.setClickListener(this)
        binding.recyclePlaces.setAdapter(placeAutoCompleteAdapter)
        placeAutoCompleteAdapter.notifyDataSetChanged()

        /*binding.etPlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (!s.length.equals("")) {
                    placeAutoCompleteAdapter.getFilter().filter(s)
                    if (placeListVisibility.get() == View.GONE) {
                        placeListVisibility.set(View.VISIBLE)
                    }
                }
                else {
                    if (placeListVisibility.get() == View.VISIBLE) {
                        placeListVisibility.set(View.GONE)
                    }
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
            }
        })*/

        binding.btSave.setOnClickListener {

        }
    }

    override fun click(place: Place) {
        address.set(place.address)
        binding.recyclePlaces.visibility = View.GONE

        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
        try {
            val addresses =
                geocoder.getFromLocation(place.getLatLng()!!.latitude, place.getLatLng()!!.longitude, 1)
            State = addresses[0].adminArea
            City = addresses[0].locality
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Toast.makeText(
            requireActivity(),
            place.getAddress() + ", " + place.getLatLng()!!.latitude + place.getLatLng()!!.longitude + ","+State+","+City,
            Toast.LENGTH_SHORT
        ).show()
    }
}