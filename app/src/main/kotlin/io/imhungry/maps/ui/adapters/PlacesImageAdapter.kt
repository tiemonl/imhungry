package io.imhungry.maps.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.viewpager.widget.PagerAdapter
import com.google.maps.GeoApiContext

class PlacesImageAdapter(
    private val geoContext: GeoApiContext,
    private val baseImageIdentifier: String,
    private val additionalImageIdentifiers: LiveData<List<String>>
) : PagerAdapter() {
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int = additionalImageIdentifiers.value?.size ?: 1


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }
}