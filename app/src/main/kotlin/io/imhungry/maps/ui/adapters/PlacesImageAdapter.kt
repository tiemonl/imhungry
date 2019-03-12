package io.imhungry.maps.ui.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.collection.LruCache
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import io.imhungry.R
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.math.roundToInt


class PlacesImageAdapter(
    context: Context,
    private val geoContext: GeoApiContext,
    private val imageIdentifiers: LiveData<List<String>>,
    private val imageCache: LruCache<String, Bitmap>
) : RecyclerView.Adapter<PlacesImageAdapter.PlaceImageViewHolder>() {

    private val job = SupervisorJob()
    private val adapterScope = CoroutineScope(Dispatchers.Main + job)

    private val inflater = LayoutInflater.from(context)

    // Convert 128 DP (Height of image carousel) to Pixels
    private val imageMaxHeight = Math.ceil(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            128f,
            context.resources.displayMetrics
        ).toDouble()
    ).roundToInt()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceImageViewHolder {
        val itemView = inflater.inflate(R.layout.list_item_image, parent, false)
        return PlaceImageViewHolder(itemView)
    }

    override fun getItemCount(): Int = imageIdentifiers.value?.size ?: 0

    override fun onBindViewHolder(holder: PlaceImageViewHolder, position: Int) {
        adapterScope.launch {
            var imageBitmap: Bitmap? = null
            withContext(Dispatchers.IO) {
                imageIdentifiers.value?.getOrNull(position)?.let {
                    imageBitmap = imageCache[it] ?: downloadImage(it)
                }
            }
            holder.imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun downloadImage(it: String): Bitmap {
        val imageResult = PlacesApi.photo(geoContext, it).maxHeight(imageMaxHeight).await()
        val image = BitmapFactory.decodeByteArray(imageResult.imageData, 0, imageResult.imageData.size)
        imageCache.put(it, image)
        return image
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        job.cancel()
    }


    inner class PlaceImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.businessImage)
    }
}