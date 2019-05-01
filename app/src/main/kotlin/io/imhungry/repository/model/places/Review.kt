package io.imhungry.repository.model.places

import io.imhungry.repository.model.user.User
import java.time.Instant

data class Review(
    val author: User,
    val comment: String,
    val created: Instant,
    val place: Place,
    val rating: Int
)