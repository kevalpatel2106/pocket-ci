package com.kevalpatel2106.repositoryImpl.di

import com.kevalpatel2106.repository.AccountRepo
import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repository.ArtifactRepo
import com.kevalpatel2106.repository.BuildRepo
import com.kevalpatel2106.repository.CIInfoRepo
import com.kevalpatel2106.repository.JobRepo
import com.kevalpatel2106.repository.ProjectRepo
import com.kevalpatel2106.repository.RemoteConfigRepo
import com.kevalpatel2106.repository.SettingsRepo
import com.kevalpatel2106.repositoryImpl.account.AccountRepoImpl
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountBasicMapper
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountBasicMapperImpl
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountDtoMapper
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountDtoMapperImpl
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountMapper
import com.kevalpatel2106.repositoryImpl.account.usecase.AccountMapperImpl
import com.kevalpatel2106.repositoryImpl.appConfig.AppConfigRepoImpl
import com.kevalpatel2106.repositoryImpl.artifact.ArtifactRepoImpl
import com.kevalpatel2106.repositoryImpl.build.BuildRepoImpl
import com.kevalpatel2106.repositoryImpl.cache.dataStore.AppDataStore
import com.kevalpatel2106.repositoryImpl.cache.dataStore.AppDataStoreImpl
import com.kevalpatel2106.repositoryImpl.cache.remoteConfig.FirebaseRemoteConfigCache
import com.kevalpatel2106.repositoryImpl.cache.remoteConfig.RemoteConfigCache
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactory
import com.kevalpatel2106.repositoryImpl.ciConnector.CIConnectorFactoryImpl
import com.kevalpatel2106.repositoryImpl.ciInfo.CIInfoRepoImpl
import com.kevalpatel2106.repositoryImpl.job.JobRepoImpl
import com.kevalpatel2106.repositoryImpl.project.ProjectRepoImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.IsProjectCacheExpired
import com.kevalpatel2106.repositoryImpl.project.usecase.IsProjectCacheExpiredImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectBasicMapper
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectBasicMapperImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectDtoMapper
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectDtoMapperImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectWithLocalDataMapper
import com.kevalpatel2106.repositoryImpl.project.usecase.ProjectWithLocalDataMapperImpl
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCache
import com.kevalpatel2106.repositoryImpl.project.usecase.SaveProjectsToCacheImpl
import com.kevalpatel2106.repositoryImpl.remoteConfig.RemoteConfigRepoImpl
import com.kevalpatel2106.repositoryImpl.setting.SettingsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ViewModelComponentBindings {

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
    abstract fun bindProjectMapper(impl: ProjectWithLocalDataMapperImpl): ProjectWithLocalDataMapper

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

    @Binds
    abstract fun bindJobRepo(impl: JobRepoImpl): JobRepo

    @Binds
    abstract fun bindArtifactRepo(impl: ArtifactRepoImpl): ArtifactRepo

    @Binds
    abstract fun bindProjectBasicMapper(impl: ProjectBasicMapperImpl): ProjectBasicMapper

    @Binds
    abstract fun bindAccountBasicMapper(impl: AccountBasicMapperImpl): AccountBasicMapper

    @Binds
    abstract fun bindAccountDtoMapper(impl: AccountDtoMapperImpl): AccountDtoMapper

    @Binds
    abstract fun bindAppRemoteConfig(impl: FirebaseRemoteConfigCache): RemoteConfigCache

    @Binds
    abstract fun bindRemoteConfigRepo(impl: RemoteConfigRepoImpl): RemoteConfigRepo
}
