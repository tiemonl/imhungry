package io.imhungry.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
data class RestaurantEntity (
    @ColumnInfo(name = "name") val name: String
) {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0
}