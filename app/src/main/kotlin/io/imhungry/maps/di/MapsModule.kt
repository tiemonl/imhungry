package io.imhungry.maps.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.imhungry.di.ViewModelKey
import io.imhungry.maps.vm.MapViewModel

@Module(includes = [MapsProviderModule::class])
abstract class MapsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun mapViewModel(viewModel: MapViewModel): ViewModel
}