package com.kevalpatel2106.projects.list.di

import com.kevalpatel2106.projects.list.usecase.InsertProjectListHeaders
import com.kevalpatel2106.projects.list.usecase.InsertProjectListHeadersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ProjectsModuleBinding {

    @Binds
    @ViewModelScoped
    abstract fun bindInsertProjectListHeaders(
        impl: InsertProjectListHeadersImpl,
    ): InsertProjectListHeaders
}
