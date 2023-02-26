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
internal interface ViewModelComponentBindings {

    @Binds
    fun bindCIRepoFactory(factory: CIConnectorFactoryImpl): CIConnectorFactory

    @Binds
    fun bindAccountRepo(impl: AccountRepoImpl): AccountRepo

    @Binds
    fun bindCISelectionRepo(impl: CIInfoRepoImpl): CIInfoRepo

    @Binds
    fun bindProjectRepo(impl: ProjectRepoImpl): ProjectRepo

    @Binds
    fun bindSettingsRepo(impl: SettingsRepoImpl): SettingsRepo

    @Binds
    fun bindBuildInfoRepo(impl: AppConfigRepoImpl): AppConfigRepo

    @Binds
    fun bindProjectMapper(impl: ProjectWithLocalDataMapperImpl): ProjectWithLocalDataMapper

    @Binds
    fun bindProjectDtoMapper(impl: ProjectDtoMapperImpl): ProjectDtoMapper

    @Binds
    fun bindAccountMapper(impl: AccountMapperImpl): AccountMapper

    @Binds
    fun bindSaveProjectsToCache(impl: SaveProjectsToCacheImpl): SaveProjectsToCache

    @Binds
    fun bindIsProjectCacheExpired(impl: IsProjectCacheExpiredImpl): IsProjectCacheExpired

    @Binds
    fun bindBuildRepo(impl: BuildRepoImpl): BuildRepo

    @Binds
    fun bindJobRepo(impl: JobRepoImpl): JobRepo

    @Binds
    fun bindArtifactRepo(impl: ArtifactRepoImpl): ArtifactRepo

    @Binds
    fun bindProjectBasicMapper(impl: ProjectBasicMapperImpl): ProjectBasicMapper

    @Binds
    fun bindAccountBasicMapper(impl: AccountBasicMapperImpl): AccountBasicMapper

    @Binds
    fun bindAccountDtoMapper(impl: AccountDtoMapperImpl): AccountDtoMapper

    @Binds
    fun bindRemoteConfigRepo(impl: RemoteConfigRepoImpl): RemoteConfigRepo
}
