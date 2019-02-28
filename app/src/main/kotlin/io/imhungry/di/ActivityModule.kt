package io.imhungry.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.imhungry.ui.LoginActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): LoginActivity
}