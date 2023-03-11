package com.kevalpatel2106.feature.drawer.di

import com.kevalpatel2106.feature.drawer.usecase.HandleBottomDrawerVMEvents
import com.kevalpatel2106.feature.drawer.usecase.HandleBottomDrawerVMEventsImpl
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
    fun bindHandleBottomDrawerVMEvents(
        impl: HandleBottomDrawerVMEventsImpl,
    ): HandleBottomDrawerVMEvents
}
