package com.kevalpatel2106.core.di

import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapperImpl
import com.kevalpatel2106.core.errorHandling.HttpErrorMessageMapper
import com.kevalpatel2106.core.errorHandling.HttpErrorMessageMapperImpl
import com.kevalpatel2106.core.paging.usecase.LoadStateMapper
import com.kevalpatel2106.core.paging.usecase.LoadStateMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CoreSingletonBindings {

    @Binds
    abstract fun bindDisplayErrorMapper(impl: DisplayErrorMapperImpl): DisplayErrorMapper

    @Binds
    abstract fun bindHttpErrorMessageMapper(
        impl: HttpErrorMessageMapperImpl,
    ): HttpErrorMessageMapper

    @Binds
    abstract fun bindLoadStateMapper(impl: LoadStateMapperImpl): LoadStateMapper
}
