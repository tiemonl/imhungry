package io.imhungry.maps.ui

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.model.PlaceType
import io.imhungry.R
import io.imhungry.maps.ui.adapters.MapItemAdapter
import io.imhungry.maps.vm.MapViewModel
import io.imhungry.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject

class MapActivity : BaseActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mapViewModel: MapViewModel

    //For Current Location
    private lateinit var map: GoogleMap
    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private lateinit var lastLocation: Location
    private var mapMarker: Marker? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        //Init Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                map.isMyLocationEnabled = true
            }
        } else {
            map.isMyLocationEnabled = true
        }
        //Enable Zoom Control
        map.uiSettings.isZoomControlsEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapViewModel = ViewModelProviders.of(this, viewModelFactory)[MapViewModel::class.java]

        val mapFragment = SupportMapFragment()
        supportFragmentManager.beginTransaction().add(R.id.mapFragmentHolder, mapFragment).commit()
        mapFragment.getMapAsync(this)

        mapItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mapItems.adapter = MapItemAdapter(this, mapViewModel.mapData)

        //Request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallBack()
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
            }
        } else {
            buildLocationRequest()
            buildLocationCallBack()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        }

        mapViewModel.mapData.observe(this, Observer { results ->
            for (result in results) {
                map.addMarker(
                    MarkerOptions()
                        .position(result.geometry.location.let { LatLng(it.lat, it.lng) })
                        .title(result.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
            }
        })
    }

    //Get Current Location
    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                lastLocation = p0.locations[p0.locations.size - 1]

                if (mapMarker != null) {
                    mapMarker?.remove()
                }

                currentLatitude = lastLocation.latitude
                currentLongitude = lastLocation.longitude

                val latLng = LatLng(currentLatitude, currentLongitude)
                map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("You are here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                ).showInfoWindow()
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                map.animateCamera(CameraUpdateFactory.zoomTo(14f))

                mapViewModel.loadNearbyPlaces(com.google.maps.model.LatLng(latLng.latitude, latLng.longitude), PlaceType.RESTAURANT)
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), MY_PERMISSION_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), MY_PERMISSION_CODE
                )
            }
            return false
        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallBack()
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper()
                            )
                            map.isMyLocationEnabled = true
                        }
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    companion object {
        private const val MY_PERMISSION_CODE: Int = 1000
    }
}
