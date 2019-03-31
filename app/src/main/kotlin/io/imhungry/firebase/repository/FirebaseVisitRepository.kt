package io.imhungry.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.firebase.model.places.FirebaseVisit
import javax.inject.Inject

class FirebaseVisitRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository(db.collection(FirebaseVisit.FIREBASE_COLLECTION_NAME_VISITS)) {

    private suspend fun queryVisits(field: String, value: String, limit: Long, startAt: Long) =
        performListQuery<FirebaseVisit>(
            collection.whereEqualTo(
                field,
                value
            ).limit(limit).startAt(startAt).get()
        )

    suspend fun getPlaceVisits(placesId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT, startAt: Long = 0) =
        queryVisits(FirebaseVisit::placesId.name, placesId, limit, startAt)

    suspend fun getLeaderVisits(userId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT, startAt: Long = 0) =
        queryVisits(FirebaseVisit::leader.name, userId, limit, startAt)

    suspend fun getUserVisits(userId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT, startAt: Long = 0) =
        performListQuery<FirebaseVisit>(
            collection.whereArrayContains(
                FirebaseVisit::users.name,
                userId
            ).limit(limit).startAt(startAt).get()
        )

    companion object {
        const val FIREBASE_VISIT_DEFAULT_LIMIT = 10L
    }
}