package io.imhungry.firebase.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class FirebaseProviderModule {

    @Provides
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()
}