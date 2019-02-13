package io.imhungry.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import io.imhungry.model.RestaurantEntity
import io.imhungry.model.RestaurantTagEntity
import io.imhungry.model.TagEntity

class ImhungryRepository(private val imhungryDao: ImhungryDao) {

    val allTags: LiveData<List<TagEntity>> = imhungryDao.getAllTags()
    val allRestaurants: LiveData<List<RestaurantEntity>> = imhungryDao.getAllRestaurants()

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