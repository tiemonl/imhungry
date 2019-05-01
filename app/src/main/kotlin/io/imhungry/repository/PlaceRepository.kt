package io.imhungry.repository

import com.google.maps.android.SphericalUtil
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceDetails
import com.google.maps.model.PlacesSearchResult
import io.imhungry.repository.model.places.Place
import io.imhungry.repository.places.PlacesApiRepository
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val placesRepo: PlacesApiRepository
) {

    suspend fun getNearby(location: LatLng) = placesRepo.getNearby(location).await().map { mapPlace(location, it) }

    suspend fun getPlace(location: LatLng, id: String) = mapPlaceDetails(location, placesRepo.getDetails(id).await())

    private fun mapPlaceDetails(location: LatLng, placeDetails: PlaceDetails) = Place(
        placeDetails.placeId,
        placeDetails.geometry.location,
        getDistance(placeDetails.geometry.location, location),
        placeDetails.icon,
        placeDetails.name,
        placeDetails.openingHours?.openNow ?: false,
        placeDetails.rating,
        placeDetails.types.map { it.toString() },
        placeDetails.photos.map { photo -> photo.photoReference },
        placeDetails.vicinity
    )

    private fun mapPlace(location: LatLng, placeResult: PlacesSearchResult) = Place(
        placeResult.placeId,
        placeResult.geometry.location,
        getDistance(placeResult.geometry.location, location),
        placeResult.icon,
        placeResult.name,
        placeResult.openingHours?.openNow ?: false,
        placeResult.rating,
        placeResult.types.toList(),
        placeResult.photos.map { photo -> photo.photoReference },
        placeResult.vicinity
    )

    private fun getDistance(location: LatLng, latLng: LatLng) = SphericalUtil.computeDistanceBetween(
        com.google.android.gms.maps.model.LatLng(location.lat, location.lng),
        com.google.android.gms.maps.model.LatLng(latLng.lat, latLng.lng)
    ) * 0.000621
}