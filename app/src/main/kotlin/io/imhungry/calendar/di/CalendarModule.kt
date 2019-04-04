package io.imhungry.calendar.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.imhungry.calendar.ui.CalendarActivity

@Module
abstract class CalendarModule {
    @ContributesAndroidInjector
    abstract fun contributeCalendarActivityInjector(): CalendarActivity
}