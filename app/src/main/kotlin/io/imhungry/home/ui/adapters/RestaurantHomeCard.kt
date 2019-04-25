package io.imhungry.home.ui.adapters

interface RestaurantHomeCardProvider {

    fun getRating(numStars: Int): Float;

    fun getName(): String;

    fun getDisplayDistanceFromCurrentLocation(): String;

}