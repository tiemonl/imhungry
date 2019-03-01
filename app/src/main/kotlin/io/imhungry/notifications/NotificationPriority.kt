package io.imhungry.notifications

import androidx.core.app.NotificationCompat

enum class NotificationPriority(internal val value: Int) {
    MIN(NotificationCompat.PRIORITY_MIN),
    LOW(NotificationCompat.PRIORITY_LOW),
    DEFAULT(NotificationCompat.PRIORITY_DEFAULT),
    HIGH(NotificationCompat.PRIORITY_HIGH),
    MAX(NotificationCompat.PRIORITY_MAX)
}