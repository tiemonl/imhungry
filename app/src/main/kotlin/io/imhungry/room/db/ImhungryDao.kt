package io.imhungry.room.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.imhungry.room.model.RestaurantEntity
import io.imhungry.room.model.RestaurantTagEntity
import io.imhungry.room.model.TagEntity

@Dao
interface ImhungryDao {

    @Insert
    fun insertTag(tag: TagEntity)

    @get:Query("SELECT * FROM tags")
    val allTags: LiveData<List<TagEntity>>

    @Insert
    fun insertRestaurant(restaurant: RestaurantEntity)

    @get:Query("SELECT * FROM restaurants")
    val allRestaurants: LiveData<List<RestaurantEntity>>

    @Insert
    fun insertRestaurantTag(restaurantTag: RestaurantTagEntity)

    @get:Query("SELECT * FROM tags_restaurant")
    val allRestaurantTags: LiveData<List<RestaurantTagEntity>>

    @Query("SELECT tag FROM tags_restaurant WHERE restaurantId = :restaurantId")
    fun getTagsForRestaurant(restaurantId: Int): LiveData<List<String>>
}