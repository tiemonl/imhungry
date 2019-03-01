package io.imhungry.maps.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.imhungry.common.di.ViewModelKey
import io.imhungry.maps.ui.MapActivity
import io.imhungry.maps.vm.MapViewModel

@Module(includes = [MapsProviderModule::class])
abstract class MapsModule {
    @ContributesAndroidInjector
    abstract fun contributeMapActivityInjector(): MapActivity

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    internal abstract fun mapViewModel(viewModel: MapViewModel): ViewModel
}