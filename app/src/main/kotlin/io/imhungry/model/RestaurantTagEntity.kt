package io.imhungry.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "restaurant_tag",
    primaryKeys = ["tag", "restaurant_id"])
data class RestaurantTagEntity(
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "restaurant_id") val id: Int
)