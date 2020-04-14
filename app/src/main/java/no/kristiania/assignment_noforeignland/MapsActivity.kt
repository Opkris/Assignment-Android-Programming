package no.kristiania.assignment_noforeignland

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import no.kristiania.assignment_noforeignland.adapters.DetailCustomViewHolder

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

        val navBarTitle = intent.getStringExtra(DetailCustomViewHolder.PLACE_NAME_KEY)
        supportActionBar?.title = navBarTitle

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
        val placeName = intent.getStringExtra(DetailCustomViewHolder.PLACE_NAME_KEY)
        val lat = intent.getDoubleExtra(DetailCustomViewHolder.PLACE_LAT_KEY,-1.32)
        val lon = intent.getDoubleExtra(DetailCustomViewHolder.PLACE_LON_KEY, -1.23)

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        val myPlace = LatLng(lat, lon)
        map.addMarker(MarkerOptions().position(myPlace).title(placeName))
        map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 15.0f))

    }
    override fun onMarkerClick(p0: Marker?) = false
  }
