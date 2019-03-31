package io.imhungry.firebase.model.user

import io.imhungry.firebase.model.user.privacy.FirebasePrivacyConfig

data class FirebaseUser(
    val privacy: FirebasePrivacyConfig,
    val profileImage: String,
    val username: String
)