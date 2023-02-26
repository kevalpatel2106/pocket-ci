package com.kevalpatel2106.connector.bitrise.di

import com.kevalpatel2106.connector.bitrise.BitriseConnector
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClientImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.AccountMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.AccountMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactListItemMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactTypeMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactTypeMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildStatusMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildStatusMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.CommitMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.CommitMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.IsoDateMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.IsoDateMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.ProjectMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.ProjectMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.PullRequestMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.PullRequestMapperImpl
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTime
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTimeImpl
import com.kevalpatel2106.connector.bitrise.usecase.SanitizeTriggeredBy
import com.kevalpatel2106.connector.bitrise.usecase.SanitizeTriggeredByImpl
import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.connector.ci.internal.CIConnectorBindingKey
import com.kevalpatel2106.entity.CIType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
internal interface BitriseBindings {

    @IntoMap
    @Binds
    @CIConnectorBindingKey(CIType.BITRISE)
    fun bindCIConnector(impl: BitriseConnector): CIConnector

    @Binds
    @ViewModelScoped
    fun bindConvertProjectsWithLastUpdateTime(
        impl: ConvertProjectsWithLastUpdateTimeImpl,
    ): ConvertProjectsWithLastUpdateTime

    @Binds
    @ViewModelScoped
    fun bindBitriseRetrofitClient(impl: BitriseRetrofitClientImpl): BitriseRetrofitClient

    @Binds
    @ViewModelScoped
    fun bindBuildStatusMapper(impl: BuildStatusMapperImpl): BuildStatusMapper

    @Binds
    @ViewModelScoped
    fun bindBuildMapper(impl: BuildMapperImpl): BuildMapper

    @Binds
    @ViewModelScoped
    fun bindProjectMapper(impl: ProjectMapperImpl): ProjectMapper

    @Binds
    @ViewModelScoped
    fun bindAccountMapper(impl: AccountMapperImpl): AccountMapper

    @Binds
    @ViewModelScoped
    fun bindIsoDateMapper(impl: IsoDateMapperImpl): IsoDateMapper

    @Binds
    @ViewModelScoped
    fun bindSanitizeTriggeredBy(impl: SanitizeTriggeredByImpl): SanitizeTriggeredBy

    @Binds
    @ViewModelScoped
    fun bindCommitMapper(impl: CommitMapperImpl): CommitMapper

    @Binds
    @ViewModelScoped
    fun bindPullRequestMapper(impl: PullRequestMapperImpl): PullRequestMapper

    @Binds
    @ViewModelScoped
    fun bindArtifactListItemMapper(impl: ArtifactListItemMapperImpl): ArtifactListItemMapper

    @Binds
    @ViewModelScoped
    fun bindArtifactTypeMapper(impl: ArtifactTypeMapperImpl): ArtifactTypeMapper
}
