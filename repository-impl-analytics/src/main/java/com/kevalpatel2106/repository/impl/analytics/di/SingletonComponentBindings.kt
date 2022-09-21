package com.kevalpatel2106.repository.impl.analytics.di

import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.impl.analytics.AnalyticsRepoImpl
import com.kevalpatel2106.repository.impl.analytics.provider.analytics.AnalyticsProvider
import com.kevalpatel2106.repository.impl.analytics.provider.analytics.FirebaseAnalyticsProvider
import com.kevalpatel2106.repository.impl.analytics.provider.authentication.FirebaseUserAuthenticationProvider
import com.kevalpatel2106.repository.impl.analytics.provider.authentication.UserAuthenticationProvider
import com.kevalpatel2106.repository.impl.analytics.provider.crashReporter.CrashReporterProvider
import com.kevalpatel2106.repository.impl.analytics.provider.crashReporter.FirebaseCrashReporterProvider
import com.kevalpatel2106.repository.impl.analytics.usecase.ShouldAuthenticateUser
import com.kevalpatel2106.repository.impl.analytics.usecase.ShouldAuthenticateUserImpl
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
    abstract fun bindFirebaseAuthenticateUser(
        impl: FirebaseUserAuthenticationProvider,
    ): UserAuthenticationProvider

    @Binds
    abstract fun bindAnalyticsProvider(impl: FirebaseAnalyticsProvider): AnalyticsProvider

    @Binds
    abstract fun bindCrashReporterProvider(
        impl: FirebaseCrashReporterProvider,
    ): CrashReporterProvider

    @Binds
    abstract fun bindShouldAuthenticateUser(
        impl: ShouldAuthenticateUserImpl,
    ): ShouldAuthenticateUser
}
