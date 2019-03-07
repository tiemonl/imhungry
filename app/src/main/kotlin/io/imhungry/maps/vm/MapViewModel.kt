package io.imhungry.maps.vm

import androidx.lifecycle.MutableLiveData
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceType
import com.google.maps.model.PlacesSearchResult
import com.google.maps.model.RankBy
import io.imhungry.common.vm.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val geoContext: GeoApiContext
) : BaseViewModel() {

    val mapData = MutableLiveData<MutableList<PlacesSearchResult>>().apply {
        value = mutableListOf()
    }

    fun loadNearbyPlaces(location: LatLng, type: PlaceType?) = launch {
        withContext(Dispatchers.IO) {
            val request = PlacesApi.nearbySearchQuery(geoContext, location)
                .rankby(RankBy.DISTANCE)
                .custom("fields", "*")
            if (type != null) {
                request.type(type)
            }

            val response = request.await()

            mapData.postValue(response.results.toMutableList())
        }
    }
}