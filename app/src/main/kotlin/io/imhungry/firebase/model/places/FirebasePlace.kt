package io.imhungry.firebase.model.places

data class FirebasePlace(
    val placesId: String
) {
    companion object {
        const val FIREBASE_COLLECTION_NAME_PLACES = "places"
    }
}