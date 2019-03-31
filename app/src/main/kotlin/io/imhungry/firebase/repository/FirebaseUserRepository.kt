package io.imhungry.firebase.repository

import com.google.firebase.firestore.FirebaseFirestore
import io.imhungry.firebase.model.user.FirebaseUser
import io.imhungry.firebase.model.user.privacy.FirebasePrivacyConfig
import javax.inject.Inject

class FirebaseUserRepository @Inject constructor(
    db: FirebaseFirestore
) : FirebaseBaseCollectionRepository<FirebaseUser>(
    db.collection(FirebaseUser.FIREBASE_COLLECTION_NAME_USERS),
    FirebaseUser::class
) {

    suspend fun addUser(user: FirebaseUser) = performInsertion(user, user.uid)

    suspend fun getUser(uid: String) = performSingleQuery(collection.whereEqualTo(FirebaseUser::uid.name, uid))

    suspend fun updateProfileImage(uid: String, imageData: String) =
        performFieldUpdate(uid, FirebaseUser::profileImage.name, imageData)

    suspend fun updateUsername(uid: String, username: String) =
        performFieldUpdate(uid, FirebaseUser::username.name, username)

    suspend fun updatePrivacy(uid: String, privacy: FirebasePrivacyConfig) =
        performFieldUpdate(uid, FirebaseUser::privacy.name, privacy)

    companion object {
        const val FIREBASE_USER_DEFAULT_LIMIT = 5L
    }
}