package io.imhungry.maps.di

import com.google.maps.GeoApiContext
import dagger.Module
import dagger.Provides

@Module
class MapsModule {
    @Provides
    fun provideGeoContext() = GeoApiContext.Builder()
        .apiKey("")
        .build()
}