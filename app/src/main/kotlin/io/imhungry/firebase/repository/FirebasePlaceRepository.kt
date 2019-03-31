package io.imhungry.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.firebase.model.places.FirebasePlace
import javax.inject.Inject

@Deprecated("This isn't presently used because we get all data from Google places, and additionally we shouldn't allow users to create places.")
class FirebasePlaceRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository(db.collection(FirebasePlace.FIREBASE_COLLECTION_NAME_PLACES)) {

    suspend fun getPlace(placesId: String) = performSingleQuery<FirebasePlace>(
        collection.whereEqualTo(
            FirebasePlace::placesId.name,
            placesId
        ).get()
    )
}