package io.imhungry.di

import android.app.Application
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.imhungry.MainApplication
import io.imhungry.db.ImhungryDao
import io.imhungry.db.ImhungryDatabase
import io.imhungry.db.ImhungryRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, RoomModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<MainApplication> {
    fun inject(app: Application)

    fun imhungryDao(): ImhungryDao

    fun imhungryDatabase(): ImhungryDatabase

    fun imhungryRepository(): ImhungryRepository

    fun application(): Application
}