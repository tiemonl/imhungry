package io.imhungry.repository

import com.google.maps.model.LatLng
import com.google.maps.model.PlaceDetails
import com.google.maps.model.PlacesSearchResult
import io.imhungry.repository.model.places.Place
import io.imhungry.repository.places.PlacesApiRepository
import javax.inject.Inject

class PlaceRepository @Inject constructor(
    private val placesRepo: PlacesApiRepository
) {

    suspend fun getNearby(location: LatLng) = placesRepo.getNearby(location).await().map(::mapPlace)

    suspend fun getPlace(id: String) = mapPlaceDetails(placesRepo.getDetails(id).await())

    private fun mapPlaceDetails(placeDetails: PlaceDetails) = Place(
        placeDetails.placeId,
        placeDetails.geometry.location,
        placeDetails.icon,
        placeDetails.name,
        placeDetails.openingHours?.openNow ?: false,
        placeDetails.rating,
        placeDetails.types.map { it.toString() },
        placeDetails.photos.map { photo -> photo.photoReference },
        placeDetails.vicinity
    )

    private fun mapPlace(placeResult: PlacesSearchResult) = Place(
        placeResult.placeId,
        placeResult.geometry.location,
        placeResult.icon,
        placeResult.name,
        placeResult.openingHours?.openNow ?: false,
        placeResult.rating,
        placeResult.types.toList(),
        placeResult.photos.map { photo -> photo.photoReference },
        placeResult.vicinity
    )
}