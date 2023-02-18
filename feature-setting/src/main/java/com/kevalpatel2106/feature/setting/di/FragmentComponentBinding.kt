package com.kevalpatel2106.feature.setting.di

import com.kevalpatel2106.feature.setting.list.usecase.PrepareAppInviteIntent
import com.kevalpatel2106.feature.setting.list.usecase.PrepareAppInviteIntentImpl
import com.kevalpatel2106.feature.setting.list.usecase.PrepareContactUsIntent
import com.kevalpatel2106.feature.setting.list.usecase.PrepareContactUsIntentImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal interface FragmentComponentBinding {

    @Binds
    @FragmentScoped
    fun bindPrepareAppInviteIntent(impl: PrepareAppInviteIntentImpl): PrepareAppInviteIntent

    @Binds
    @FragmentScoped
    fun bindPrepareContactUsIntent(impl: PrepareContactUsIntentImpl): PrepareContactUsIntent
}
