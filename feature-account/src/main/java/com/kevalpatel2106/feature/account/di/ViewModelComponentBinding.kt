package com.kevalpatel2106.feature.account.di

import com.kevalpatel2106.feature.account.list.usecase.ConvertToAccountItem
import com.kevalpatel2106.feature.account.list.usecase.ConvertToAccountItemImpl
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeaders
import com.kevalpatel2106.feature.account.list.usecase.InsertAccountListHeadersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ViewModelComponentBinding {

    @Binds
    @ViewModelScoped
    abstract fun bindInsertAccountListHeaders(
        impl: InsertAccountListHeadersImpl,
    ): InsertAccountListHeaders

    @Binds
    @ViewModelScoped
    abstract fun bindConvertToAccountItem(
        impl: ConvertToAccountItemImpl,
    ): ConvertToAccountItem
}