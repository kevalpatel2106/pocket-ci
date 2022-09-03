package com.kevalpatel2106.pocketci.di

import com.kevalpatel2106.pocketci.BuildConfig
import com.kevalpatel2106.repository.di.IsDebug
import com.kevalpatel2106.repositoryImpl.di.AppVersion
import com.kevalpatel2106.repositoryImpl.di.AppVersionCode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppSingletonModule {

    // Reading from BuildConfig of App module
    @Singleton
    @Provides
    @IsDebug
    fun provideIsDebug() = BuildConfig.DEBUG

    // Reading from BuildConfig of App module
    @Singleton
    @Provides
    @AppVersion
    fun provideAppVersion() = BuildConfig.VERSION_NAME

    // Reading from BuildConfig of App module
    @Singleton
    @Provides
    @AppVersionCode
    fun provideAppVersionCode() = BuildConfig.VERSION_CODE
}
