package io.imhungry.repository.model.places

import com.google.maps.model.LatLng
import java.net.URL

data class Place(
    val placeId: String,
    val location: LatLng,
    val distance: Double,
    val iconUrl: URL,
    val name: String,
    val isOpen: Boolean,
    val rating: Float,
    val types: List<String>,
    val photoReferences: List<String>,
    val address: String
)