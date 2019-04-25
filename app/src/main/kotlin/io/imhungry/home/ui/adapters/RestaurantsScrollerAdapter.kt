package io.imhungry.home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.imhungry.R

class RestaurantsScrollerAdapter(private val restaurants: ArrayList<RestaurantHomeCardProvider>) :
    RecyclerView.Adapter<RestaurantsScrollerAdapter.RestaurantPreviewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class RestaurantPreviewHolder(val cardLayout: LinearLayout) :
        RecyclerView.ViewHolder(cardLayout) {
        val restaurantName = cardLayout.findViewById<TextView>(R.id.restaurantName)
        val ratingBar = cardLayout.findViewById<RatingBar>(R.id.ratingBar)
        val distance = cardLayout.findViewById<TextView>(R.id.restaurantDistance)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantsScrollerAdapter.RestaurantPreviewHolder {
        // create a new view
        val cardLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_card, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters

        return RestaurantPreviewHolder(cardLayout)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: RestaurantPreviewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        val restaurant = restaurants[position]
        holder.restaurantName.text = restaurant.getName()
        holder.ratingBar.rating = restaurant.getRating(holder.ratingBar.numStars)
        holder.distance.text = restaurant.getDisplayDistanceFromCurrentLocation()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = restaurants.size
}