package io.imhungry.restaurantLists.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.imhungry.restaurantLists.ui.RestaurantListsActivtiy

@Module
abstract class RestaurantListsModule {
    @ContributesAndroidInjector
    abstract fun contributesRestaurantListsActivityInjector(): RestaurantListsActivtiy
}