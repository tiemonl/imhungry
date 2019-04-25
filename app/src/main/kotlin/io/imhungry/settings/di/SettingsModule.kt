package io.imhungry.settings.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.imhungry.settings.SettingsActivity

@Module
abstract class SettingsModule {
    @ContributesAndroidInjector
    abstract fun contributeSettingsActivityInjector(): SettingsActivity
}