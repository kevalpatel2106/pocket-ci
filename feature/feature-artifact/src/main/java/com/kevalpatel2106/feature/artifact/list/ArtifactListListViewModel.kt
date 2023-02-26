package com.kevalpatel2106.feature.artifact.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.coreViews.networkStateAdapter.NetworkStateCallback
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RefreshArtifacts
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.RetryLoading
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowArtifactRemoved
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadNotSupported
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadQueuedMessage
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowErrorLoadingArtifact
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListAdapterCallback
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem
import com.kevalpatel2106.feature.artifact.list.usecase.ArtifactItemMapper
import com.kevalpatel2106.feature.artifact.usecase.DownloadFileFromUrl
import com.kevalpatel2106.repository.ArtifactRepo
import com.kevalpatel2106.repository.CIInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class ArtifactListListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val ciInfoRepo: CIInfoRepo,
    private val artifactRepo: ArtifactRepo,
    artifactItemMapper: ArtifactItemMapper,
    private val downloadFileFromUrl: DownloadFileFromUrl,
    private val displayErrorMapper: DisplayErrorMapper,
) : BaseViewModel<ArtifactListVMEvent>(), ArtifactListAdapterCallback, NetworkStateCallback {
    private val navArgs = ArtifactListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow(ArtifactListViewState.initialState(navArgs.title))
    val viewState = _viewState.asStateFlow()

    val pageViewState: Flow<PagingData<ArtifactListItem>> = artifactRepo.getArtifacts(
        accountId = navArgs.accountId.toAccountId(),
        projectId = navArgs.projectId.toProjectId(),
        buildId = navArgs.buildId.toBuildId(),
    ).map { pagedData ->
        @Suppress("USELESS_CAST")
        pagedData.map { artifact -> artifactItemMapper(artifact) as ArtifactListItem }
    }.catch { error ->
        _vmEventsFlow.emit(ShowErrorLoadingArtifact(displayErrorMapper(error)))
    }.cachedIn(viewModelScope)

    fun reload() = viewModelScope.launch { _vmEventsFlow.emit(RefreshArtifacts) }

    override fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    override fun retryNextPage() {
        viewModelScope.launch { _vmEventsFlow.emit(RetryLoading) }
    }

    override fun onDownloadClicked(artifact: Artifact) {
        viewModelScope.launch {
            val ciInfo = ciInfoRepo.getCIInfo(navArgs.accountId.toAccountId())
            if (!ciInfo.supportDownloadArtifacts) {
                _vmEventsFlow.emit(ShowDownloadNotSupported)
                return@launch
            }

            _viewState.modify { copy(showDownloadingLoader = true) }
            runCatching {
                artifactRepo.getArtifactDownloadUrl(
                    accountId = navArgs.accountId.toAccountId(),
                    projectId = navArgs.projectId.toProjectId(),
                    buildId = navArgs.buildId.toBuildId(),
                    artifactId = artifact.id,
                )
            }.onSuccess { url ->
                _viewState.modify { copy(showDownloadingLoader = false) }
                if (url.url == Url.EMPTY) {
                    _vmEventsFlow.emit(ShowArtifactRemoved)
                } else {
                    downloadFileFromUrl(url, artifact.name)
                    _vmEventsFlow.emit(ShowDownloadQueuedMessage)
                }
            }.onFailure { error ->
                Timber.e(error)
                _viewState.modify { copy(showDownloadingLoader = false) }
                _vmEventsFlow.emit(ShowDownloadFailed(displayErrorMapper(error)))
            }
        }
    }
}
