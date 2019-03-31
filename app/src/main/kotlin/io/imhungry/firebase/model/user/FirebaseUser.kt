package io.imhungry.firebase.model.user

import io.imhungry.firebase.model.user.privacy.FirebasePrivacyConfig
import io.imhungry.firebase.model.user.privacy.FirebasePrivacyLevel.PUBLIC

data class FirebaseUser(
    val userId: String,
    val username: String,
    val privacy: FirebasePrivacyConfig = FirebasePrivacyConfig(
        favorites = PUBLIC,
        friendsList = PUBLIC,
        history = PUBLIC,
        profile = PUBLIC
    ),
    val profileImage: String?
)