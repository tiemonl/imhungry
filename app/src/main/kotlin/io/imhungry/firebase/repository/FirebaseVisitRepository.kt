package io.imhungry.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.firebase.model.places.FirebaseVisit
import javax.inject.Inject

class FirebaseVisitRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository(db.collection(FirebaseVisit.FIREBASE_COLLECTION_NAME_VISITS)) {

    suspend fun getPlaceVisits(placesId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT) =
        performListQuery<FirebaseVisit>(
            collection.whereEqualTo(
                FirebaseVisit::placesId.name,
                placesId
            ).limit(limit).get()
        )

    companion object {
        const val FIREBASE_VISIT_DEFAULT_LIMIT = 10L
    }
}