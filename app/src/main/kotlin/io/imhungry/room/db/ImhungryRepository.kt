package io.imhungry.room.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import io.imhungry.room.model.RestaurantEntity
import io.imhungry.room.model.RestaurantTagEntity
import io.imhungry.room.model.TagEntity

class ImhungryRepository(private val imhungryDao: ImhungryDao) {

    val allTags: LiveData<List<TagEntity>> = imhungryDao.allTags
    val allRestaurants: LiveData<List<RestaurantEntity>> = imhungryDao.allRestaurants

    @WorkerThread
    fun insertTag(tag: TagEntity) {
        imhungryDao.insertTag(tag)
    }

    @WorkerThread
    fun insertRestaurant(restaurant: RestaurantEntity) {
        imhungryDao.insertRestaurant(restaurant)
    }

    @WorkerThread
    fun insertRestaurantTag(restaurantTag: RestaurantTagEntity) {
        imhungryDao.insertRestaurantTag(restaurantTag)
    }

    @WorkerThread
    fun getTagsForRestaurant(id: Int) {
        imhungryDao.getTagsForRestaurant(id)
    }
}