package io.imhungry.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "restaurantTag",
    primaryKeys = ["tag", "restaurantId"])
data class RestaurantTagEntity(
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "restaurantId") val id: Int
)