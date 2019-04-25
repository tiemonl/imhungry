package io.imhungry.home.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.imhungry.R

class HomeCardAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeCardAdapter.HomeViewHolder>() {

    private val restaurantLists = ArrayList<listGroup>()

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string
    class HomeViewHolder(private val restaurantListView: LinearLayout) :
        RecyclerView.ViewHolder(restaurantListView) {
        val restaurantsRecycler = restaurantListView.findViewById<RecyclerView>(R.id.restaurantList)
        val titleBar = restaurantListView.findViewById<TextView>(R.id.restaurantListTitle)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeCardAdapter.HomeViewHolder {
        // create a new view
        val RetaurantsLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_card_list, parent, false) as LinearLayout
        // set the view's size, margins, paddings and layout parameters

        val retVal = HomeViewHolder(RetaurantsLayout)

        retVal.restaurantsRecycler.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        return retVal
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.restaurantsRecycler.swapAdapter(RestaurantsScrollerAdapter(restaurantLists[position].list), true)
        holder.titleBar.text = restaurantLists[position].name
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = restaurantLists.size

    fun addList(name: String, list: ArrayList<RestaurantHomeCardProvider>) {
        restaurantLists.add(listGroup(name, list))
    }

    private data class listGroup(val name: String, val list: ArrayList<RestaurantHomeCardProvider>)
}