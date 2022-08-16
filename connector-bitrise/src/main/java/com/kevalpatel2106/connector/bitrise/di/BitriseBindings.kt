package com.kevalpatel2106.connector.bitrise.di

import com.kevalpatel2106.connector.bitrise.BitriseConnector
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
import com.kevalpatel2106.connector.bitrise.network.mapper.IsoDateMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.IsoDateMapperImpl
import com.kevalpatel2106.connector.bitrise.network.mapper.ProjectMapper
import com.kevalpatel2106.connector.bitrise.network.mapper.ProjectMapperImpl
import com.kevalpatel2106.connector.bitrise.usecase.CommitMapper
import com.kevalpatel2106.connector.bitrise.usecase.CommitMapperImpl
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTime
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTimeImpl
import com.kevalpatel2106.connector.bitrise.usecase.PullRequestMapper
import com.kevalpatel2106.connector.bitrise.usecase.PullRequestMapperImpl
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
internal abstract class BitriseBindings {

    @IntoMap
    @Binds
    @CIConnectorBindingKey(CIType.BITRISE)
    abstract fun bindCIConnector(impl: BitriseConnector): CIConnector

    @Binds
    @ViewModelScoped
    abstract fun bindConvertProjectsWithLastUpdateTime(
        impl: ConvertProjectsWithLastUpdateTimeImpl,
    ): ConvertProjectsWithLastUpdateTime

    @Binds
    @ViewModelScoped
    abstract fun bindBuildStatusMapper(impl: BuildStatusMapperImpl): BuildStatusMapper

    @Binds
    @ViewModelScoped
    abstract fun bindBuildMapper(impl: BuildMapperImpl): BuildMapper

    @Binds
    @ViewModelScoped
    abstract fun bindProjectMapper(impl: ProjectMapperImpl): ProjectMapper

    @Binds
    @ViewModelScoped
    abstract fun bindAccountMapper(impl: AccountMapperImpl): AccountMapper

    @Binds
    @ViewModelScoped
    abstract fun bindIsoDateMapper(impl: IsoDateMapperImpl): IsoDateMapper

    @Binds
    @ViewModelScoped
    abstract fun bindSanitizeTriggeredBy(impl: SanitizeTriggeredByImpl): SanitizeTriggeredBy

    @Binds
    @ViewModelScoped
    abstract fun bindCommitMapper(impl: CommitMapperImpl): CommitMapper

    @Binds
    @ViewModelScoped
    abstract fun bindPullRequestMapper(impl: PullRequestMapperImpl): PullRequestMapper

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactListItemMapper(
        impl: ArtifactListItemMapperImpl
    ): ArtifactListItemMapper

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactTypeMapper(impl: ArtifactTypeMapperImpl): ArtifactTypeMapper
}
