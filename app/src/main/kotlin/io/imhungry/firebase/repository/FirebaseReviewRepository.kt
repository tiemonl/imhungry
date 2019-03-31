package io.imhungry.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.firebase.model.places.FirebaseReview
import io.imhungry.firebase.model.user.FirebaseUser
import javax.inject.Inject

class FirebaseReviewRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository(db.collection(FirebaseReview.FIREBASE_COLLECTION_NAME_REVIEWS)) {

    private suspend fun queryReviews(field: String, value: String, limit: Long, startAt: Long) =
        performListQuery<FirebaseReview>(
            collection.whereEqualTo(
                field,
                value
            ).limit(limit).startAt(startAt).get()
        )

    suspend fun getPlaceReviews(placesId: String, limit: Long = FIREBASE_REVIEW_DEFAULT_LIMIT, startAt: Long = 0) =
        queryReviews(FirebaseReview::placesId.name, placesId, limit, startAt)

    suspend fun getUserReviews(user: FirebaseUser, limit: Long = FIREBASE_REVIEW_DEFAULT_LIMIT, startAt: Long = 0) =
        queryReviews(FirebaseReview::author.name, user.userId, limit, startAt)

    companion object {
        const val FIREBASE_REVIEW_DEFAULT_LIMIT = 5L
    }
}