package com.kevalpatel2106.connector.github.di

import com.kevalpatel2106.connector.ci.CIConnector
import com.kevalpatel2106.connector.ci.internal.CIConnectorBindingKey
import com.kevalpatel2106.connector.github.GitHubConnector
import com.kevalpatel2106.connector.github.network.mapper.AccountMapper
import com.kevalpatel2106.connector.github.network.mapper.AccountMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.github.network.mapper.ArtifactListItemMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.ArtifactTypeMapper
import com.kevalpatel2106.connector.github.network.mapper.ArtifactTypeMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.BuildMapper
import com.kevalpatel2106.connector.github.network.mapper.BuildMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.BuildStatusMapper
import com.kevalpatel2106.connector.github.network.mapper.BuildStatusMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.CommitMapper
import com.kevalpatel2106.connector.github.network.mapper.CommitMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.IsoDateMapper
import com.kevalpatel2106.connector.github.network.mapper.IsoDateMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.JobMapper
import com.kevalpatel2106.connector.github.network.mapper.JobMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.ProjectMapper
import com.kevalpatel2106.connector.github.network.mapper.ProjectMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.PullRequestMapper
import com.kevalpatel2106.connector.github.network.mapper.PullRequestMapperImpl
import com.kevalpatel2106.connector.github.network.mapper.StepMapper
import com.kevalpatel2106.connector.github.network.mapper.StepMapperImpl
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilderImpl
import com.kevalpatel2106.entity.CIType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class GitHubBindings {

    @IntoMap
    @Binds
    @CIConnectorBindingKey(CIType.GITHUB)
    abstract fun bindCIConnector(impl: GitHubConnector): CIConnector

    @Binds
    @ViewModelScoped
    abstract fun bindAccountMapper(impl: AccountMapperImpl): AccountMapper

    @Binds
    @ViewModelScoped
    abstract fun bindProjectMapper(impl: ProjectMapperImpl): ProjectMapper

    @Binds
    @ViewModelScoped
    abstract fun bindBuildStatusMapper(impl: BuildStatusMapperImpl): BuildStatusMapper

    @Binds
    @ViewModelScoped
    abstract fun bindBuildMapper(impl: BuildMapperImpl): BuildMapper

    @Binds
    @ViewModelScoped
    abstract fun bindCommitMapper(impl: CommitMapperImpl): CommitMapper

    @Binds
    @ViewModelScoped
    abstract fun bindIsoDateMapper(impl: IsoDateMapperImpl): IsoDateMapper

    @Binds
    @ViewModelScoped
    abstract fun bindPullRequestMapper(impl: PullRequestMapperImpl): PullRequestMapper

    @Binds
    @ViewModelScoped
    abstract fun bindJobMapper(impl: JobMapperImpl): JobMapper

    @Binds
    @ViewModelScoped
    abstract fun bindStepMapper(impl: StepMapperImpl): StepMapper

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactListItemMapper(
        impl: ArtifactListItemMapperImpl,
    ): ArtifactListItemMapper

    @Binds
    @ViewModelScoped
    abstract fun bindArtifactTypeMapper(impl: ArtifactTypeMapperImpl): ArtifactTypeMapper

    @Binds
    @ViewModelScoped
    abstract fun bindTokenHeaderValueBuilder(
        impl: TokenHeaderValueBuilderImpl,
    ): TokenHeaderValueBuilder
}
