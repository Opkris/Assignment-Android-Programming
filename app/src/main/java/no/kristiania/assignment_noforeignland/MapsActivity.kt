package no.kristiania.assignment_noforeignland

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException


// not added to the map:
// Receiving Location Updates


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {


    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private val TAG = "MapsActivity"


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val placeName = intent.getStringExtra(DetailLessonViewHolder.PLACE_NAME_KEY)
        val lat = intent.getDoubleExtra(DetailLessonViewHolder.PLACE_LAT_KEY,-1.32)
        val lon = intent.getDoubleExtra(DetailLessonViewHolder.PLACE_LON_KEY, -1.23)

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        val myPlace = LatLng(lat, lon)
        map.addMarker(MarkerOptions().position(myPlace).title(placeName))
        map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 15.0f))

//        setUpMap()
    }


    override fun onMarkerClick(p0: Marker?) = false


    //the code checks if the app has been granted the ACCESS_FINE_LOCATION permission. if it hasn't,
    // then request it from user.
//    private fun setUpMap() {
//
//
//
//            if (ActivityCompat.checkSelfPermission(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this,
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//                return
//            }

//        if (ActivityCompat.checkSelfPermission(this,
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//            return
//        }
//
//        map.isMyLocationEnabled = true

        //The Android Maps API provides different map types to help you out:
        // MAP_TYPE_NORMAL,
        // MAP_TYPE_SATELLITE,
        // MAP_TYPE_TERRAIN,
        // MAP_TYPE_HYBRID
//        map.mapType = GoogleMap.MAP_TYPE_TERRAIN

        // 1
//        map.isMyLocationEnabled = true

// 2
//        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
//            if (location != null) {
//                lastLocation = location
//                val currentLatLng = LatLng(location.latitude, location.longitude)
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
//            }
//        }
//
//    }


//    private fun placeMarkerOnMap(location: LatLng) {
//        val markerOptions = MarkerOptions().position(location)
//
//        val titleStr = getAddress(location)  // add these two lines
//        markerOptions.title(titleStr)
//
//        map.addMarker(markerOptions)
//    }


//    private fun getAddress(latLng: LatLng): String {
//        // 1
//        val geocoder = Geocoder(this)
//        val addresses: List<Address>?
//        val address: Address?
//        var addressText = ""
//
//        try {
//            // 2
//            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//            // 3
//            if (null != addresses && !addresses.isEmpty()) {
//                address = addresses[0]
//                for (i in 0 until address.maxAddressLineIndex) {
//                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
//                }
//            }
//        } catch (e: IOException) {
//            Log.e(TAG, e.localizedMessage)
//        }
//
//        return addressText
//    }


}
