package io.imhungry.maps.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.collection.LruCache
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.GeoApiContext
import com.google.maps.PlaceDetailsRequest
import com.google.maps.PlacesApi
import com.google.maps.model.PlacesSearchResult
import io.imhungry.R
import kotlinx.coroutines.*

class MapItemAdapter(
    private val context: Context,
    private val geoContext: GeoApiContext,
    private val items: LiveData<MutableList<PlacesSearchResult>>,
    private val map: GoogleMap
) : RecyclerView.Adapter<MapItemAdapter.RestaurantViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val imageCache = LruCache<String, Bitmap>(50)

    private val job = SupervisorJob()
    private val adapterScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_restaurant, parent, false)
        return RestaurantViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.value?.size ?: 0

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = items.value?.get(position)
        holder.itemView.setOnClickListener {
            map.animateCamera(CameraUpdateFactory.newLatLng(item?.geometry?.location?.let { loc ->
                LatLng(
                    loc.lat,
                    loc.lng
                )
            }))
            Toast.makeText(
                it.context,
                "Restaurant Name: ${item?.name}",
                Toast.LENGTH_LONG
            ).show()
        }

        val imagesLiveDate = MutableLiveData<List<String>>()

        adapterScope.launch {
            withContext(Dispatchers.IO) {
                val details =
                    PlacesApi.placeDetails(geoContext, item?.placeId).fields(PlaceDetailsRequest.FieldMask.PHOTOS)
                        .await()
                imagesLiveDate.postValue(details.photos.map { it.photoReference })
            }
            holder.imagesView.adapter?.notifyDataSetChanged()
        }

        holder.imagesView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.imagesView.adapter = PlacesImageAdapter(context, geoContext, imagesLiveDate, imageCache)

        holder.itemTitle.text = item?.name
        holder.ratingValue.text = context.getString(R.string.business_rating_template, item?.rating)
        // TODO Add cost icons and calculate distance
        holder.distance.text = context.getString(R.string.business_distance_template_miles, 0.0)
        holder.businessDescription.text = ""
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        job.cancel()
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagesView: RecyclerView = itemView.findViewById(R.id.businessImages)

        val itemTitle: TextView = itemView.findViewById(R.id.businessName)

        val ratingValue: TextView = itemView.findViewById(R.id.ratingValue)

        val distance: TextView = itemView.findViewById(R.id.distance)

        val businessDescription: TextView = itemView.findViewById(R.id.businessDescription)
    }
}