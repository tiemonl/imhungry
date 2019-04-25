package io.imhungry.launcher.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.imhungry.launcher.ui.LauncherActivity

@Module
abstract class LauncherModule {
    @ContributesAndroidInjector
    abstract fun contributeLauncherActivityInjector(): LauncherActivity
}