package com.kevalpatel2106.feature.account.di

import com.kevalpatel2106.feature.account.list.eventHandler.AccountListVmEventHandler
import com.kevalpatel2106.feature.account.list.eventHandler.AccountListVmEventHandlerImpl
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
    fun bindAccountListVmEventHandler(
        impl: AccountListVmEventHandlerImpl
    ): AccountListVmEventHandler
}
