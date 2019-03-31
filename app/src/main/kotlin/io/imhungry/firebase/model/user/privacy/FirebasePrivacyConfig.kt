package io.imhungry.firebase.model.user.privacy

data class FirebasePrivacyConfig(
    val favorites: FirebasePrivacyLevel,
    val friendsList: FirebasePrivacyLevel,
    val history: FirebasePrivacyLevel,
    val profile: FirebasePrivacyLevel
)