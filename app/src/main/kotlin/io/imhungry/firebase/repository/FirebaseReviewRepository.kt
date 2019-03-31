package io.imhungry.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.firebase.model.places.FirebaseReview
import javax.inject.Inject

class FirebaseReviewRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository(db.collection(FirebaseReview.FIREBASE_COLLECTION_NAME_REVIEWS)) {

    suspend fun getPlaceReviews(placesId: String, limit: Long = FIREBASE_REVIEW_DEFAULT_LIMIT) =
        performListQuery<FirebaseReview>(
            collection.whereEqualTo(
                FirebaseReview::placesId.name,
                placesId
            ).limit(limit).get()
        )

    suspend fun getUserReviews(userId: String, limit: Long = FIREBASE_REVIEW_DEFAULT_LIMIT) =
        performListQuery<FirebaseReview>(
            collection.whereEqualTo(
                FirebaseReview::author.name,
                userId
            ).limit(limit).get()
        )

    companion object {
        const val FIREBASE_REVIEW_DEFAULT_LIMIT = 5L
    }
}