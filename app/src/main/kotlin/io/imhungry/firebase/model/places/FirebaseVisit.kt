package io.imhungry.firebase.model.places

import com.google.firebase.Timestamp

data class FirebaseVisit(
    val ended: Timestamp,
    val placesId: String,
    val started: Timestamp,
    val leader: String,
    val users: List<String>
) {
    companion object {
        const val FIREBASE_COLLECTION_NAME_VISITS = "visits"
    }
}