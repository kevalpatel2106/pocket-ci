package com.kevalpatel2106.feature.build.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.entity.Build
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenBuildArtifacts
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenBuildLogs
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenJobs
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenMarkDownViewer
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.ShowErrorAndClose
import com.kevalpatel2106.feature.build.detail.usecase.BuildDetailViewStateMapper
import com.kevalpatel2106.repository.CIInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BuildDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    ciInfoRepo: CIInfoRepo,
    private val displayErrorMapper: DisplayErrorMapper,
    private val viewStateMapper: BuildDetailViewStateMapper,
) : ViewModel() {
    private val navArgs = BuildDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val build: Build = navArgs.build

    private val _vmEventsFlow = MutableSharedFlow<BuildDetailVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow(viewStateMapper(build, null))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching {
                ciInfoRepo.getCIInfo(navArgs.accountId)
            }.onSuccess { ciInfo ->
                _viewState.update { viewStateMapper(build, ciInfo) }
            }.onFailure { error ->
                Timber.e(error)
                _vmEventsFlow.emit(ShowErrorAndClose(displayErrorMapper(error)))
            }
        }
    }

    fun onOpenBuildLogs() = viewModelScope.launch {
        _vmEventsFlow.emit(
            OpenBuildLogs(
                accountId = navArgs.accountId,
                projectId = navArgs.build.projectId,
                buildId = navArgs.build.id,
            ),
        )
    }

    fun onOpenJobs() = viewModelScope.launch {
        _vmEventsFlow.emit(
            OpenJobs(
                accountId = navArgs.accountId,
                projectId = navArgs.build.projectId,
                buildId = navArgs.build.id,
                title = "${build.workflow.name} (#${build.number})",
            ),
        )
    }

    fun onOpenArtifacts() = viewModelScope.launch {
        _vmEventsFlow.emit(
            OpenBuildArtifacts(
                accountId = navArgs.accountId,
                projectId = navArgs.build.projectId,
                buildId = navArgs.build.id,
                title = "${build.workflow.name} (#${build.number})",
            ),
        )
    }

    fun onViewCommitMessageClicked() = viewModelScope.launch {
        val commitMsg = navArgs.build.commit?.message
        requireNotNull(commitMsg) { "Commit message is null. Cannot open full screen view." }
        _vmEventsFlow.emit(
            OpenMarkDownViewer(
                titleRes = R.string.build_detail_commit_markdown_view_title,
                commitMessage = commitMsg,
            ),
        )
    }

    fun onViewAbortMessageClicked() = viewModelScope.launch {
        val abortMessage = navArgs.build.abortReason
        requireNotNull(abortMessage) { "Abort reason is null. Cannot open full screen view." }
        _vmEventsFlow.emit(
            OpenMarkDownViewer(
                titleRes = R.string.build_detail_commit_markdown_view_title,
                commitMessage = abortMessage,
            ),
        )
    }
}
