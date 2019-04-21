package io.imhungry.repository.model.places

import io.imhungry.repository.model.user.User
import java.time.Instant

data class Visit(
    val ended: Instant?,
    val place: Place,
    val started: Instant,
    val leader: User,
    val users: List<User>
)