package io.imhungry.repository.firebase.model.user

import io.imhungry.repository.common.model.user.privacy.PrivacyConfig

data class FirebaseUser(
    val uid: String,
    val username: String,
    val privacy: PrivacyConfig = PrivacyConfig(),
    val profileImage: String?,
    val friends: List<String>?,
    val favorites: List<String>?
) {
    companion object {
        const val FIREBASE_COLLECTION_NAME_USERS = "users"
    }
}