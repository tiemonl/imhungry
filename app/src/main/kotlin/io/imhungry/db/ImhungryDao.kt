package io.imhungry.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dagger.Component
import io.imhungry.di.RoomModule
import io.imhungry.model.RestaurantEntity
import io.imhungry.model.RestaurantTagEntity
import io.imhungry.model.TagEntity

@Dao
interface ImhungryDao {

    @Insert
    fun insertTag(tag: TagEntity)

    @Query("SELECT * FROM tag")
    fun getAllTags(): LiveData<List<TagEntity>>

    @Insert
    fun insertRestaurant(restaurant: RestaurantEntity)

    @Query("SELECT * FROM restaurant")
    fun getAllRestaurants(): LiveData<List<RestaurantEntity>>

    @Insert
    fun insertRestaurantTag(restaurantTag: RestaurantTagEntity)

    @Query("SELECT * FROM restaurant_tag")
    fun getAllRestaurantTags(): LiveData<List<RestaurantTagEntity>>

    @Query("SELECT tag FROM restaurant_tag WHERE restaurant_id = :restaurant_id")
    fun getTagsForRestaurant(restaurant_id: Int): LiveData<List<String>>
}