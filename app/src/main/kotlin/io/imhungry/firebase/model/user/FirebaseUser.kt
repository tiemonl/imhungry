package io.imhungry.firebase.model.user

import io.imhungry.firebase.model.user.privacy.FirebasePrivacyConfig
import io.imhungry.firebase.model.user.privacy.FirebasePrivacyLevel.PUBLIC

data class FirebaseUser(
    val uid: String,
    val username: String,
    val privacy: FirebasePrivacyConfig = FirebasePrivacyConfig(
        favorites = PUBLIC,
        friendsList = PUBLIC,
        history = PUBLIC
    ),
    val profileImage: String?,
    val friends: List<String>?,
    val favorites: List<String>?
) {
    companion object {
        const val FIREBASE_COLLECTION_NAME_USERS = "users"
    }
}