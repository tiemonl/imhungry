package io.imhungry.repository.places

import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceType
import com.google.maps.model.RankBy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import javax.inject.Inject

class PlacesApiRepository @Inject constructor(
    private val geoApiContext: GeoApiContext
) {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun getNearby(location: LatLng) = scope.async {
        PlacesApi.nearbySearchQuery(geoApiContext, location)
            .rankby(RankBy.DISTANCE)
            .type(PlaceType.RESTAURANT)
            .custom("fields", "*").await().results
    }

    fun getDetails(id: String) = scope.async {
        PlacesApi.placeDetails(geoApiContext, id).await()
    }
}