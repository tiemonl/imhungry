package io.imhungry.common.di

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.imhungry.MainApplication
import io.imhungry.home.di.HomeModule
import io.imhungry.maps.di.MapsModule
import io.imhungry.room.di.RoomModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RoomModule::class,
        ViewModelModule::class,
        MapsModule::class,
        HomeModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {
    fun inject(app: Application)
}