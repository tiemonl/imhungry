package io.imhungry.ui


import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import io.imhungry.GoogleAPI.IGoogleAPIService
import io.imhungry.GoogleAPI.initiateGoogleAPIService
import io.imhungry.model.RootObject
import kotlinx.android.synthetic.main.activity_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import io.imhungry.R


class MyMapsActivity : AppCompatActivity(), OnMapReadyCallback {


    //For Current Location
    private lateinit var mMap: GoogleMap
    private var currentLatitude = 0.0
    private var currentLongitude = 0.0
    private lateinit var lastLocation: Location
    private var mapMarker: Marker? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    companion object {
        private const val MY_PERMISSION_CODE: Int = 1000
    }

    //For Nearby Places
    lateinit var googleAPIService: IGoogleAPIService
    internal lateinit var nearbyPlaces: RootObject


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Init Google Play Services
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            mMap!!.isMyLocationEnabled = true
        }

        //Enable Zoom Control
        mMap.uiSettings.isZoomControlsEnabled = true



    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Notify when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //Init Service
        googleAPIService = initiateGoogleAPIService.googleAPIService

        //Request runtime permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
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

    }



    //Get Current Location
    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                lastLocation = p0!!.locations[p0!!.locations.size - 1]

                if(mapMarker != null) {
                    mapMarker!!.remove()
                }

                currentLatitude = lastLocation.latitude
                currentLongitude = lastLocation.longitude

                val latLng = LatLng(currentLatitude, currentLongitude)
                mMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("You are here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                    .showInfoWindow()
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(14f))


                //Get Nearby Places
                nearByPlaces("restaurant")

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
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSION_CODE)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ), MY_PERMISSION_CODE)
            }
            return false

        } else {
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            MY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallBack()
                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                            mMap!!.isMyLocationEnabled = true
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





    //Get Nearby Places
    private fun nearByPlaces(typePlace: String) {

        //Clear all markers
        //mMap.clear()

        //build URL request base on location
        val url = getUrl(currentLatitude, currentLongitude, typePlace)



        googleAPIService.getNearbyPlaces(url)
            .enqueue(object: Callback<RootObject> {
                override fun onFailure(call: Call<RootObject>, t: Throwable) {
                    Toast.makeText(baseContext,"" + t!!.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RootObject>, response: Response<RootObject>) {
                    nearbyPlaces = response!!.body()!!

                    if(response!!.isSuccessful) {

                        //Log.d("SAM", nearbyPlaces.results!!.size.toString())
                        //Store All information that probably use later
                        val totalFoundPlaces = nearbyPlaces.results!!.size
                        val arrayName = ArrayList<String>()
                        val arrayOpeningNow = ArrayList<Boolean>()
                        val arrayPriceLevel = ArrayList<Int>()
                        val arrayRating = ArrayList<Double>()
                        val arrayType = ArrayList<Array<String>>()
                        val arrayTotalRating = ArrayList<Int>()
                        val arrayVicinity = ArrayList<String>()
                        val arrayLatitude = ArrayList<Double>()
                        val arrayLongitude = ArrayList<Double>()

                        for(i in 0 until response!!.body()!!.results!!.size) {
                            val jsonResults = response.body()!!.results!![i]

                            //arrayName.add("${i + 1}. ${jsonResults.name}")
                            arrayOpeningNow.add(jsonResults.opening_hours!!.open_now!!)
                            arrayPriceLevel.add(jsonResults.price_level)
                            arrayRating.add(jsonResults.rating)
                            arrayType.add(jsonResults.types!!)
                            arrayTotalRating.add(jsonResults.user_ratings_total)
                            arrayVicinity.add(jsonResults.vicinity!!)
                            arrayLatitude.add(jsonResults.geometry!!.location!!.lat)
                            arrayLongitude.add(jsonResults.geometry!!.location!!.lng)


                            //Calcuate Distance between current location and found location
                            val foundLocation = Location("")
                            foundLocation.latitude = arrayLatitude[i]
                            foundLocation.longitude = arrayLongitude[i]

                            val currentLocation = Location("")
                            currentLocation.latitude = currentLatitude
                            currentLocation.longitude = currentLongitude

                            //Convert from Meter to Mile: 0.000621371
                            val distanceInMiles = "%.2f".format(foundLocation.distanceTo(currentLocation) * 0.000621371)
                            arrayName.add("${i + 1}. ${jsonResults.name} (Distance: $distanceInMiles mile)")
                        }



                        //Add Markers on Map
                        val total = arrayName.count() - 1
                        for (i in 0..total) {
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(arrayLatitude[i], arrayLongitude[i]))
                                    .title(arrayName[i])
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                        }


                        //Paste Results into ListView
                        myListView.adapter = ArrayAdapter<String>(
                            this@MyMapsActivity,
                            android.R.layout.simple_list_item_1,
                            arrayName
                        )


                        //On Click Listener
                        myListView.setOnItemClickListener { parent, view, position, id ->
                            Toast.makeText(this@MyMapsActivity, "Restaurant Name: ${arrayName[position]}", Toast.LENGTH_LONG).show()
                        }

                    }
                }

            })


    }



    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=2000")  //2km
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=${getString(R.string.google_maps_key)}")

        return googlePlaceUrl.toString()
    }


}
