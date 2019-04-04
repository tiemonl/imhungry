package io.imhungry.restaurantLists.ui

import android.os.Bundle
import io.imhungry.R
import io.imhungry.common.ui.NavigationActivity

class RestaurantListsActivtiy : NavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_lists_activtiy)
    }
}
