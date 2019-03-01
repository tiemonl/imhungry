package io.imhungry.maps.di

import android.app.Application
import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides
import io.imhungry.R

@Module
class MapsProviderModule {
    @Provides
    fun provideGeoContext(app: Application) = GeoApiContext.Builder()
        .apiKey(app.getString(R.string.google_maps_key))
        .build()
}