package com.kevalpatel2106.repository.impl.analytics.di

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonComponentModule {

    @Provides
    @Singleton
    fun provideCrashalytics() = FirebaseCrashlytics.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(application: Application) =
        FirebaseAnalytics.getInstance(application)

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}
