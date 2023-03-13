package com.kevalpatel2106.feature.drawer.di

import com.kevalpatel2106.feature.drawer.drawer.eventHandler.BottomDrawerVMEventHandler
import com.kevalpatel2106.feature.drawer.drawer.eventHandler.BottomDrawerVMEventHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
internal interface FragmentBinding {

    @Binds
    @FragmentScoped
    fun bindHandleBottomDrawerVMEvents(
        impl: BottomDrawerVMEventHandlerImpl,
    ): BottomDrawerVMEventHandler
}
