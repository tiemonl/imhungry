package io.imhungry.common.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideApplication() = app

    @Provides
    fun provideContext(app: Application) = app.applicationContext

    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}