package com.kevalpatel2106.feature.artifact.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.usecase.SecondsTicker
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.Url
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowArtifactRemoved
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadNotSupported
import com.kevalpatel2106.feature.artifact.list.ArtifactListVMEvent.ShowDownloadQueuedMessage
import com.kevalpatel2106.feature.artifact.list.adapter.ArtifactListItem
import com.kevalpatel2106.feature.artifact.list.usecase.ArtifactItemMapper
import com.kevalpatel2106.feature.artifact.usecase.DownloadFileFromUrl
import com.kevalpatel2106.repository.ArtifactRepo
import com.kevalpatel2106.repository.CIInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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
    private val secondsTicker: SecondsTicker,
) : ViewModel() {
    private val navArgs = ArtifactListFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _vmEventsFlow = MutableSharedFlow<ArtifactListVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(ArtifactListViewState.initialState(navArgs.title))
    val viewState = _viewState.asStateFlow()

    val pageViewState: Flow<PagingData<ArtifactListItem>> = artifactRepo.getArtifacts(
        accountId = navArgs.accountId.toAccountId(),
        projectId = navArgs.projectId.toProjectId(),
        buildId = navArgs.buildId.toBuildId(),
    ).cachedIn(viewModelScope)
        .flowOn(Dispatchers.Default)
        .onEach { _viewState.update { it.copy(isRefreshing = false) } }
        .flatMapLatest { pagedData -> secondsTicker().map { pagedData } }
        .map { pagedData ->
            @Suppress("USELESS_CAST")
            pagedData.map { artifact -> artifactItemMapper(artifact) as ArtifactListItem }
        }

    fun reload() = _viewState.update { it.copy(isRefreshing = true) }

    fun close() {
        viewModelScope.launch { _vmEventsFlow.emit(Close) }
    }

    fun onDownloadClicked(artifact: Artifact) {
        viewModelScope.launch {
            val ciInfo = ciInfoRepo.getCIInfo(navArgs.accountId.toAccountId())
            if (!ciInfo.supportDownloadArtifacts) {
                _vmEventsFlow.emit(ShowDownloadNotSupported)
                return@launch
            }
            _viewState.update { it.copy(showDownloadingLoader = true) }

            runCatching {
                artifactRepo.getArtifactDownloadUrl(
                    accountId = navArgs.accountId.toAccountId(),
                    projectId = navArgs.projectId.toProjectId(),
                    buildId = navArgs.buildId.toBuildId(),
                    artifactId = artifact.id,
                )
            }.onSuccess { url ->
                _viewState.update { it.copy(showDownloadingLoader = false) }
                if (url.url == Url.EMPTY) {
                    _vmEventsFlow.emit(ShowArtifactRemoved)
                } else {
                    downloadFileFromUrl(url, artifact.name)
                    _vmEventsFlow.emit(ShowDownloadQueuedMessage)
                }
            }.onFailure { error ->
                Timber.e(error)
                _viewState.update { it.copy(showDownloadingLoader = false) }
                _vmEventsFlow.emit(ShowDownloadFailed(displayErrorMapper(error)))
            }
        }
    }
}
