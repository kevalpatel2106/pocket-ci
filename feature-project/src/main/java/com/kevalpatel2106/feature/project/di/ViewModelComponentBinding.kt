package com.kevalpatel2106.feature.project.di

import com.kevalpatel2106.feature.project.list.usecase.InsertProjectListHeaders
import com.kevalpatel2106.feature.project.list.usecase.InsertProjectListHeadersImpl
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
    abstract fun bindInsertProjectListHeaders(
        impl: InsertProjectListHeadersImpl,
    ): InsertProjectListHeaders
}
