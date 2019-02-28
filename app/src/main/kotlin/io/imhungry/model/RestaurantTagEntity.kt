package io.imhungry.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tags_restaurant")
data class RestaurantTagEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tag: String,
    val restaurantId: Int
)