package io.imhungry.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.imhungry.model.DummyEntity

@Database(entities = [DummyEntity::class], version = 1) // TODO Replace and remove DummyEntity with proper class
abstract class ImhungryDatabase : RoomDatabase() {
    abstract val imhungryDao: ImhungryDao

    companion object {
        private lateinit var instance: ImhungryDatabase

        @Synchronized
        internal fun createInstance(context: Context) = Room
            .databaseBuilder(context, ImhungryDatabase::class.java, "Imhungry_database")
            .build().apply {
                // Synchronization is no issue here, since createInstance will only be called once (by Dagger)
                instance = this
            }
    }
}