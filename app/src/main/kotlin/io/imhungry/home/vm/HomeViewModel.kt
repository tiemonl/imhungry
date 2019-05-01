package io.imhungry.home.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.maps.model.LatLng
import io.imhungry.common.vm.BaseViewModel
import io.imhungry.repository.PlaceRepository
import io.imhungry.repository.model.places.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : BaseViewModel() {

    private val nearbyMutableLiveData: MutableLiveData<List<Place>> = MutableLiveData()
    val nearbyLiveData: LiveData<List<Place>>
        get() = nearbyMutableLiveData

    private val highestRatingsMutableLiveData: MutableLiveData<List<Place>> = MutableLiveData()
    val highestRatingsLiveData: LiveData<List<Place>>
        get() = highestRatingsMutableLiveData

    init {
        launch(Dispatchers.IO) {
            val nearbyPlaces = placeRepository.getNearby(TOTALLY_NOT_HARDCODED_LOCATION)
            nearbyMutableLiveData.postValue(nearbyPlaces.sortedBy { it.distance })
            highestRatingsMutableLiveData.postValue(nearbyPlaces.sortedByDescending { it.rating })
        }
    }

    companion object {
        private val TOTALLY_NOT_HARDCODED_LOCATION = LatLng(39.031105, -84.466863)
    }
}