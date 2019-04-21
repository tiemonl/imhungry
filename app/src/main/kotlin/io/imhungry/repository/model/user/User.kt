package io.imhungry.repository.model.user

import android.graphics.drawable.Drawable
import io.imhungry.repository.common.model.user.privacy.PrivacyConfig
import io.imhungry.repository.model.places.Place

data class User(
    val uid: String,
    val username: String,
    val privacy: PrivacyConfig = PrivacyConfig(),
    val profileImage: Drawable?, // TODO Add default profile image, remove nullable
    val friends: List<User> = emptyList(),
    val favorites: List<Place> = emptyList()
)