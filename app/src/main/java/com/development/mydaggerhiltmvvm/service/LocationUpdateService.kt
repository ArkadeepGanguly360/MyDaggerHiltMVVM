package com.development.mydaggerhiltmvvm.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.development.mydaggerhiltmvvm.util.UserSharedPrefrence
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest
import java.io.IOException
import java.util.*

class LocationUpdateService : Service() {

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    var loc: String = ""

    override fun onCreate() {
        super.onCreate()
        requestLocationUpdates()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent()
        broadcastIntent.action = "restartservice"
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun requestLocationUpdates() {
        val request = LocationRequest.create()
        request.interval = 30000
        request.fastestInterval = 10000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this)

        val permission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (permission == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val location: Location = locationResult.lastLocation
                    val service = getSystemService(LOCATION_SERVICE) as LocationManager
                    val enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                        Log.d("Location Service", "location update  ${location.latitude} ${location.longitude}")
                        val geocoder = Geocoder(this@LocationUpdateService, Locale.getDefault())
                        try {
                            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            if(addresses.size>0)
                            loc = addresses[0].locality + "," + addresses[0]./*countryName*/adminArea
                            Log.d("Location Service", "location Loc  ${loc}")
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        val pref = UserSharedPrefrence.getInstance(this@LocationUpdateService)
                        pref.setUserCurrentLocation(location)
                        pref.setCURRENT_ADDRESS(loc)
                    }
                }
            }, null)
        }
    }
}