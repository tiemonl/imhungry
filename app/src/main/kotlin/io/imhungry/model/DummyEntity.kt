package io.imhungry.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dummy_table")
data class DummyEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int
)