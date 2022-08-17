package com.kevalpatel2106.feature.artifact.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.coreViews.TimeDifferenceTextView.TimeDifferenceData
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RefreshArtifacts
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RetryLoading
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadQueuedMessage
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactAdapterCallback
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem.ArtifactItem
import com.kevalpatel2106.feature.artifact.usecase.ArtifactIconMapper
import com.kevalpatel2106.feature.artifact.usecase.ArtifactSizeConverter
import com.kevalpatel2106.feature.artifact.usecase.DownloadFileFromUrl
import com.kevalpatel2106.repository.ArtifactRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ArtifactListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val artifactRepo: ArtifactRepo,
    artifactSizeConverter: ArtifactSizeConverter,
    artifactIconMapper: ArtifactIconMapper,
    private val downloadFileFromUrl: DownloadFileFromUrl,
) : BaseViewModel<ArtifactListVMEvent>(), ArtifactAdapterCallback, NetworkStateCallback {
    private val navArgs = ArtifactListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(ArtifactListViewState.initialState(navArgs.title))
    val viewState = _viewState.asStateFlow()

    val pageViewState: Flow<PagingData<ArtifactListItem>> = artifactRepo
        .getArtifacts(
            accountId = navArgs.accountId.toAccountId(),
            projectId = navArgs.projectId.toProjectId(),
            buildId = navArgs.buildId.toBuildId(),
        )
        .map { pagedData ->
            pagedData.map { artifact ->
                @Suppress("USELESS_CAST")
                ArtifactItem(
                    artifact = artifact,
                    humanReadableSize = artifactSizeConverter(artifact.sizeBytes),
                    artifactIcon = artifactIconMapper(artifact.type),
                    createdTimeDifferenceData = artifact.createdAt?.let {
                        TimeDifferenceData(dateOfEventStart = it, dateOfEventEnd = null)
                    },
                ) as ArtifactListItem
            }
        }
        .cachedIn(viewModelScope)

    fun reload() = viewModelScope.launch { _vmEventsFlow.emit(RefreshArtifacts) }

    override fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }

    override fun onDownloadClicked(artifact: Artifact) {
        viewModelScope.launch {
            _viewState.modify { copy(showDownloadingLoader = true) }
            runCatching {
                val url = artifactRepo.getArtifactDownloadUrl(
                    accountId = navArgs.accountId.toAccountId(),
                    projectId = navArgs.projectId.toProjectId(),
                    buildId = navArgs.buildId.toBuildId(),
                    artifactId = artifact.id,
                )
                Timber.i(url.value)
                url
            }.onSuccess { url ->
                downloadFileFromUrl(url, artifact.name)
                _viewState.modify { copy(showDownloadingLoader = false) }
                _vmEventsFlow.emit(ShowDownloadQueuedMessage)
            }.onFailure {
                _viewState.modify { copy(showDownloadingLoader = false) }
                _vmEventsFlow.emit(ShowDownloadFailed)
                Timber.e(it)
            }
        }
    }
}
