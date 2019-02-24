package io.imhungry.di

import android.app.Application
import dagger.Module
import dagger.Provides
import io.imhungry.db.ImhungryDao
import io.imhungry.db.ImhungryDatabase
import io.imhungry.db.ImhungryRepository
import javax.inject.Singleton

@Module
class RoomModule(application: Application) {
    private val imhungryDatabase = ImhungryDatabase.createInstance(application)

    @Singleton
    @Provides
    fun providesRoomDatabase() = imhungryDatabase

    @Singleton
    @Provides
    fun providesImhungryDao(database: ImhungryDatabase) = database.imhungryDao

    @Singleton
    @Provides
    fun providesImhungryRepository(imhungryDao: ImhungryDao) = ImhungryRepository(imhungryDao)
}