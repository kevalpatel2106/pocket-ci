package com.kevalpatel2106.feature.artifact.di

import android.app.Application
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class SingletonModule {

    @Provides
    @Singleton
    fun provideDownloadManager(application: Application) =
        application.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
}
