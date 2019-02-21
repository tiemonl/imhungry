package io.imhungry.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.imhungry.R

class NotificationHelper(private val context: Context) {

    /**
     *  Unfortunately, NotificationCompat does actually have an ENUM behind the scenes.
     *  This simplifies the function arguments in this class.
     */
    enum class Priority constructor(internal val value: Int) {
        MIN(NotificationCompat.PRIORITY_MIN),
        LOW(NotificationCompat.PRIORITY_LOW),
        DEFAULT(NotificationCompat.PRIORITY_DEFAULT),
        HIGH(NotificationCompat.PRIORITY_HIGH),
        MAX(NotificationCompat.PRIORITY_MAX)
    }

    /**
     * Need to yell at the user right away? This is your function!
     * Just pass what you want to say. That's it.
     */
    fun sendNotificationNow(
        title: String,
        message: String,
        intent: PendingIntent?,
        priority: Priority,
        notificationID: Int
    ) {
        scheduleNotification(
            getStarterNotificationBuilder(title, message, priority).setContentIntent(intent),
            notificationID
        )
    }

    /**
     * All you have to do is create the notification, this will take care of the rest.
     */
    fun scheduleNotification(
        notificationBuilder: NotificationCompat.Builder, notificationID: Int
    ) {
        initNotifications()
        with(NotificationManagerCompat.from(context)) {
            notify(notificationID, notificationBuilder.build())
        }
    }

    /**
     * Removing the boiler plate from building a notification
     * Not only does this take care of starting to create a notification, but it
     * also gives you the opportunity to customize the notification if desired.
     */
    fun getStarterNotificationBuilder(
        title: String,
        message: String,
        priority: Priority
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(this.context, context.getString(R.string.CHANNEL_ID))
            .setSmallIcon(R.drawable.ic_silverware)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(priority.value)
            // Clear the notification once tapped
            .setAutoCancel(true)
    }

    /**
     * In Android API level 26+, a notification channel must be used to send a notification.
     * the NotificationChannel class is new and not in the support library
     *
     * As per: https://developer.android.com/training/notify-user/build-notification#kotlin
     * This function can be called numerous times. If the channel is already created the system takes no action.
     */
    private fun initNotifications() {
        // Create the NotificationChannel, but only on API 26+ because
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                context.getString(R.string.CHANNEL_ID),
                context.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.channel_description)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
