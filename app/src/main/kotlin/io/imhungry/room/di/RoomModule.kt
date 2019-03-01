package io.imhungry.room.di

import android.app.Application
import dagger.Module
import dagger.Provides
import io.imhungry.room.db.ImhungryDao
import io.imhungry.room.db.ImhungryDatabase
import io.imhungry.room.db.ImhungryRepository
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application) = ImhungryDatabase.createInstance(application)

    @Singleton
    @Provides
    fun providesImhungryDao(database: ImhungryDatabase) = database.imhungryDao

    @Singleton
    @Provides
    fun providesImhungryRepository(imhungryDao: ImhungryDao) = ImhungryRepository(imhungryDao)
}