package io.imhungry.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.imhungry.model.RestaurantEntity
import io.imhungry.model.RestaurantTagEntity
import io.imhungry.model.TagEntity

@Dao
interface ImhungryDao {

    @Insert
    fun insertTag(tag: TagEntity)

    @get:Query("SELECT * FROM tag")
    val allTags: LiveData<List<TagEntity>>

    @Insert
    fun insertRestaurant(restaurant: RestaurantEntity)

    @get:Query("SELECT * FROM restaurant")
    val allRestaurants: LiveData<List<RestaurantEntity>>

    @Insert
    fun insertRestaurantTag(restaurantTag: RestaurantTagEntity)

    @get:Query("SELECT * FROM restaurantTag")
    val allRestaurantTags: LiveData<List<RestaurantTagEntity>>

    @Query("SELECT tag FROM restaurantTag WHERE restaurantId = :restaurantId")
    fun getTagsForRestaurant(restaurantId: Int): LiveData<List<String>>
}