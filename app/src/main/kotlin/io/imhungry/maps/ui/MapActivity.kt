package io.imhungry.maps.ui

import android.content.pm.PackageManager
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
import io.imhungry.common.ui.BaseActivity
import io.imhungry.common.ui.NavigationActivity
import kotlinx.android.synthetic.main.activity_map.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MapActivity : NavigationActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mapViewModel: MapViewModel

    private lateinit var map: GoogleMap
    private var mapMarker: Marker? = null

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(
            this
        )
    }
    private val locationRequest: LocationRequest = LocationRequest()
        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        .setInterval(30000)
        .setFastestInterval(30000)
        .setSmallestDisplacement(10f)
    private val locationCallback = MapLocationCallback()

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        registerLocationUpdatesPrivileged()

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

        map.uiSettings.isZoomControlsEnabled = true

        mapItems.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mapItems.adapter = MapItemAdapter(this, mapViewModel.mapData, map)

        mapViewModel.mapData.observe(this, Observer { results ->
            map.clear()
            for (result in results) {
                map.addMarker(
                    MarkerOptions()
                        .position(result.geometry.location.let { LatLng(it.lat, it.lng) })
                        .title(result.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                )
            }
            mapItems.adapter?.notifyDataSetChanged()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        mapViewModel = ViewModelProviders.of(this, viewModelFactory)[MapViewModel::class.java]

        val mapFragment = SupportMapFragment()
        supportFragmentManager.beginTransaction().add(R.id.mapFragmentHolder, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun registerLocationUpdatesPrivileged() {
        if (checkLocationPermission()) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
    }

    private fun checkLocationPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return true
        }

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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (checkLocationPermission()) {
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
                    finish()
                }
            }
        }
    }

    inner class MapLocationCallback : LocationCallback() {
        private var lastUpdate = 0L

        override fun onLocationResult(p0: LocationResult) {
            val location = p0.locations[p0.locations.size - 1]

            if (mapMarker != null) {
                mapMarker?.remove()
            }

            val latLng = LatLng(location.latitude, location.longitude)
            mapMarker = map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("You are here")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            map.animateCamera(CameraUpdateFactory.zoomTo(14f))

            if (System.currentTimeMillis() - lastUpdate > NEARBY_CACHE_TIME) {
                mapViewModel.loadNearbyPlaces(
                    com.google.maps.model.LatLng(
                        latLng.latitude,
                        latLng.longitude
                    ), PlaceType.RESTAURANT
                )
                lastUpdate = System.currentTimeMillis()
            }
        }
    }

    companion object {
        private val NEARBY_CACHE_TIME = TimeUnit.MINUTES.toMillis(3)

        private const val MY_PERMISSION_CODE: Int = 1000
    }
}
