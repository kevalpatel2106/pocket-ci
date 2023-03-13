package com.kevalpatel2106.registration.di

import com.kevalpatel2106.registration.ciSelection.eventHandler.CISelectionVmEventHandler
import com.kevalpatel2106.registration.ciSelection.eventHandler.CISelectionVmEventHandlerImpl
import com.kevalpatel2106.registration.register.eventHandler.RegisterVmEventHandler
import com.kevalpatel2106.registration.register.eventHandler.RegisterVmEventHandlerImpl
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
    fun bindCISelectionVmEventHandler(
        impl: CISelectionVmEventHandlerImpl
    ): CISelectionVmEventHandler

    @Binds
    @FragmentScoped
    fun bindRegisterVmEventHandler(impl: RegisterVmEventHandlerImpl): RegisterVmEventHandler
}
