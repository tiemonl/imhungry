package io.imhungry.repository.di

import dagger.Module
import io.imhungry.repository.firebase.di.FirebaseProviderModule

@Module(includes = [FirebaseProviderModule::class])
class RepositoryProviderModule