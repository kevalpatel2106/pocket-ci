package com.kevalpatel2106.feature.account.di

import com.kevalpatel2106.feature.account.list.usecase.AccountItemMapper
import com.kevalpatel2106.feature.account.list.usecase.AccountItemMapperImpl
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeadersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal interface ViewModelComponentBinding {

    @Binds
    @ViewModelScoped
    fun bindInsertAccountListHeaders(
        impl: InsertAccountListHeadersImpl,
    ): InsertAccountListHeaders

    @Binds
    @ViewModelScoped
    fun bindAccountItemMapper(impl: AccountItemMapperImpl): AccountItemMapper
}
