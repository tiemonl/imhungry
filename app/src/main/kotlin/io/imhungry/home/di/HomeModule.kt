package io.imhungry.home.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import io.imhungry.common.di.ViewModelKey
import io.imhungry.home.ui.HomeActivity
import io.imhungry.home.vm.HomeViewModel

@Module
abstract class HomeModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivityInjector(): HomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel
}