package io.imhungry.home.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.imhungry.R
import io.imhungry.repository.model.places.Place

class HomeCardAdapter(private val context: Context) :
    RecyclerView.Adapter<HomeCardAdapter.HomeViewHolder>() {

    private val restaurantLists = mutableMapOf<String, List<Place>>()

    inner class HomeViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val restaurantsRecycler: RecyclerView = view.findViewById(R.id.restaurantList)
        val titleBar: TextView = view.findViewById(R.id.restaurantListTitle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewHolder {
        val restaurantLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.restaurant_card_list, parent, false)

        return HomeViewHolder(restaurantLayout).also {
            it.restaurantsRecycler.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        }
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        restaurantLists.entries.elementAtOrNull(position)?.let {
            holder.restaurantsRecycler.swapAdapter(RestaurantsScrollerAdapter(it.value), true)
            holder.titleBar.text = it.key
        }
    }

    override fun getItemCount() = restaurantLists.size

    fun addList(name: String, list: List<Place>) {
        restaurantLists[name] = list
        notifyDataSetChanged()
    }
}