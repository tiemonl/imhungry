package io.imhungry.di

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.imhungry.MainApplication
import io.imhungry.di.ActivityModule
import io.imhungry.di.ViewModelModule
import io.imhungry.db.ImhungryDao
import io.imhungry.db.ImhungryDatabase
import io.imhungry.di.AppModule
import io.imhungry.di.RoomModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, RoomModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<MainApplication> {
    fun inject(app: Application)

    fun imhungryDao(): ImhungryDao

    fun imhungryDatabase(): ImhungryDatabase

    // TODO Add repository here
    // fun imhungryRepository(): ImhungryRepository

    fun application(): Application
}