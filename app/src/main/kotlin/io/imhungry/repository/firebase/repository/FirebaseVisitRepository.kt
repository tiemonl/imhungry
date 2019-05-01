package io.imhungry.repository.firebase.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.repository.firebase.model.places.FirebaseVisit
import javax.inject.Inject

class FirebaseVisitRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository<FirebaseVisit>(
    db.collection(FirebaseVisit.FIREBASE_COLLECTION_NAME_VISITS),
    FirebaseVisit::class
) {

    private suspend fun queryVisits(field: String, value: String, limit: Long, startAt: Long) =
        performListQuery(
            collection.whereEqualTo(
                field,
                value
            ).limit(limit).startAt(startAt)
        )

    suspend fun getPlaceVisits(placesId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT, startAt: Long = 0) =
        queryVisits(FirebaseVisit::placesId.name, placesId, limit, startAt)

    suspend fun getLeaderVisits(userId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT, startAt: Long = 0) =
        queryVisits(FirebaseVisit::leaderUid.name, userId, limit, startAt)

    suspend fun getUserVisits(userId: String, limit: Long = FIREBASE_VISIT_DEFAULT_LIMIT, startAt: Long = 0) =
        performListQuery(
            collection.whereArrayContains(
                FirebaseVisit::userUids.name,
                userId
            ).limit(limit).startAt(startAt)
        )

    suspend fun startVisit(visit: FirebaseVisit) = performInsertion(visit)

    suspend fun endVisit(visitId: String) = performFieldUpdate(visitId, FirebaseVisit::ended.name, Timestamp.now())

    companion object {
        const val FIREBASE_VISIT_DEFAULT_LIMIT = 10L
    }
}