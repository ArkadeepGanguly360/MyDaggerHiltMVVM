package com.development.mydaggerhiltmvvm.view.fragment.dashboard_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.development.mydaggerhiltmvvm.R
import com.development.mydaggerhiltmvvm.databinding.FragmentMapBinding
import com.development.mydaggerhiltmvvm.view.fragment.base_fragment.BaseFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import java.util.*

class MapFragment : BaseFragment() {

    private lateinit var binding: FragmentMapBinding
    var map: GoogleMap? = null
    var location: LatLng? = null
    var currentLocMarker: Marker? = null

    private val callback1 = OnMapReadyCallback { googleMap ->
            map = googleMap
            map!!.uiSettings.isMyLocationButtonEnabled = false
            if (ActivityCompat.checkSelfPermission(
                    baseActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    baseActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@OnMapReadyCallback
            }

            currentLocMarker = spotUserOnMap(map!!, userPref.getUserCurrentLocation())
            currentLocMarker!!.tag = "You"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) {
            binding = putContentView(R.layout.fragment_map, layoutInflater, container)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback1)
    }
}