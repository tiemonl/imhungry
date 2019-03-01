package io.imhungry.notifications.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.imhungry.notifications.NotificationHelper

@Module
class NotificationModule {
    @Provides
    fun provideNotificationHelper(context: Context) = NotificationHelper(context)
}