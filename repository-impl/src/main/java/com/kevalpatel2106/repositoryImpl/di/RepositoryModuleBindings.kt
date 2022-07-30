package com.kevalpatel2106.repositoryImpl.di

import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repository.ProjectRepo
import com.kevalpatel2106.repository.SettingsRepo
import com.kevalpatel2106.repositoryImpl.account.AccountRepoImpl
import com.kevalpatel2106.repositoryImpl.appConfig.AppConfigRepoImpl
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl
import com.kevalpatel2106.repositoryImpl.cache.dataStore.AppDataStore
import com.kevalpatel2106.repositoryImpl.cache.dataStore.AppDataStoreImpl
import com.kevalpatel2106.repositoryImpl.cache.db.mapper.AccountMapper
import com.kevalpatel2106.repositoryImpl.cache.db.mapper.AccountMapperImpl
import com.kevalpatel2106.repositoryImpl.cache.db.mapper.ProjectDtoMapper
import com.kevalpatel2106.repositoryImpl.cache.db.mapper.ProjectDtoMapperImpl
import com.kevalpatel2106.repositoryImpl.cache.db.mapper.ProjectMapper
import com.kevalpatel2106.repositoryImpl.cache.db.mapper.ProjectMapperImpl
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactoryImpl
import com.kevalpatel2106.repositoryImpl.ciInfo.CIInfoRepoImpl
import com.kevalpatel2106.repositoryImpl.project.ProjectRepoImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.IsProjectCacheExpired
import com.kevalpatel2106.repositoryImpl.project.usecase.IsProjectCacheExpiredImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCache
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCacheImpl
import com.kevalpatel2106.repositoryImpl.setting.SettingsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class RepositoryModuleBindings {

    @Binds
    abstract fun bindCIRepoFactory(factory: CIConnectorFactoryImpl): CIConnectorFactory

    @Binds
    abstract fun bindAccountRepo(impl: AccountRepoImpl): AccountRepo

    @Binds
    abstract fun bindCISelectionRepo(impl: CIInfoRepoImpl): CIInfoRepo

    @Binds
    abstract fun bindAppDataStore(dataStore: AppDataStoreImpl): AppDataStore

    @Binds
    abstract fun bindProjectRepo(impl: ProjectRepoImpl): ProjectRepo

    @Binds
    abstract fun bindSettingsRepo(impl: SettingsRepoImpl): SettingsRepo

    @Binds
    abstract fun bindBuildInfoRepo(impl: AppConfigRepoImpl): AppConfigRepo

    @Binds
    abstract fun bindProjectMapper(impl: ProjectMapperImpl): ProjectMapper

    @Binds
    abstract fun bindProjectDtoMapper(impl: ProjectDtoMapperImpl): ProjectDtoMapper

    @Binds
    abstract fun bindAccountMapper(impl: AccountMapperImpl): AccountMapper

    @Binds
    abstract fun bindSaveProjectsToCache(impl: SaveProjectsToCacheImpl): SaveProjectsToCache

    @Binds
    abstract fun bindIsProjectCacheExpired(impl: IsProjectCacheExpiredImpl): IsProjectCacheExpired

    @Binds
    abstract fun bindBuildRepo(impl: BuildRepoImpl): BuildRepo
}
