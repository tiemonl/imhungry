package io.imhungry.common.di

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.imhungry.MainApplication
import io.imhungry.calendar.di.CalendarModule
import io.imhungry.firebase.di.FirebaseProviderModule
import io.imhungry.home.di.HomeModule
import io.imhungry.maps.di.MapsModule
import io.imhungry.notifications.di.NotificationModule
import io.imhungry.restaurantLists.di.RestaurantListsModule
import io.imhungry.room.di.RoomModule
import io.imhungry.settings.di.SettingsModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        ViewModelModule::class,
        MapsModule::class,
        HomeModule::class,
        NotificationModule::class,
        FirebaseProviderModule::class,
        SettingsModule::class,
        CalendarModule::class,
        RestaurantListsModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {
    fun inject(app: Application)
}