package com.kevalpatel2106.settings.di

import com.kevalpatel2106.settings.list.usecase.PrepareAppInviteIntent
import com.kevalpatel2106.settings.list.usecase.PrepareAppInviteIntentImpl
import com.kevalpatel2106.settings.list.usecase.PrepareContactUsIntent
import com.kevalpatel2106.settings.list.usecase.PrepareContactUsIntentImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal abstract class FragmentComponentBinding {

    @Binds
    @FragmentScoped
    abstract fun bindPrepareAppInviteIntent(
        impl: PrepareAppInviteIntentImpl,
    ): PrepareAppInviteIntent

    @Binds
    @FragmentScoped
    abstract fun bindPrepareContactUsIntent(
        impl: PrepareContactUsIntentImpl,
    ): PrepareContactUsIntent
}
