package com.kevalpatel2106.repository.impl.analytics.di

import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.impl.analytics.AnalyticsRepoImpl
import com.kevalpatel2106.repository.impl.analytics.provider.AnalyticsProvider
import com.kevalpatel2106.repository.impl.analytics.provider.FirebaseAnalyticsProvider
import com.kevalpatel2106.repository.impl.analytics.usecase.FirebaseAuthenticateUser
import com.kevalpatel2106.repository.impl.analytics.usecase.FirebaseAuthenticateUserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SingletonComponentBindings {

    @Binds
    abstract fun bindAnalyticsRepo(impl: AnalyticsRepoImpl): AnalyticsRepo

    @Binds
    abstract fun bindFirebaseAuthenticateUser(impl: FirebaseAuthenticateUserImpl): FirebaseAuthenticateUser

    @Binds
    abstract fun bindAnalyticsProvider(impl: FirebaseAnalyticsProvider): AnalyticsProvider
}
