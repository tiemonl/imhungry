package io.imhungry.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.imhungry.ui.LoginActivity

@Module
abstract class ActivityModule {

    /*

    Below is an example of adding an Activity for injection. Simply copy below, replace X with whatever activity class you are using, and voila.

    @ContributesAndroidInjector
    abstract fun contributeXInjector(): X
    */

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): LoginActivity
}