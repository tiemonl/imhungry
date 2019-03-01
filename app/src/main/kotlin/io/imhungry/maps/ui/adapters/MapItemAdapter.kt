package io.imhungry.maps.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.PlacesSearchResult
import io.imhungry.R

class MapItemAdapter(
    context: Context,
    private val items: LiveData<MutableList<PlacesSearchResult>>,
    private val map: GoogleMap
) : RecyclerView.Adapter<MapItemAdapter.RestaurantViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_restaurant, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.value?.size ?: 0

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = items.value?.get(position)
        holder.itemView.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.newLatLng(item?.geometry?.location?.let { loc -> LatLng(loc.lat, loc.lng) }))
            Toast.makeText(
                it.context,
                "Restaurant Name: ${item?.name}",
                Toast.LENGTH_LONG
            ).show()
        }
        holder.itemTitle.text = item?.name
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
    }
}