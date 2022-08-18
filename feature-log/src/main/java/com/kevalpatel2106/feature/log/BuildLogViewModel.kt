package com.kevalpatel2106.feature.log

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toJobIdOrNull
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.log.BuildLogVMEvent.Close
import com.kevalpatel2106.feature.log.BuildLogVMEvent.CreateLogFile
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ErrorSavingLog
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ScrollToBottom
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ScrollToTop
import com.kevalpatel2106.feature.log.BuildLogViewState.Empty
import com.kevalpatel2106.feature.log.BuildLogViewState.Error
import com.kevalpatel2106.feature.log.BuildLogViewState.Success
import com.kevalpatel2106.feature.log.usecase.CalculateTextScale
import com.kevalpatel2106.feature.log.usecase.CalculateTextScale.Companion.DEFAULT_SCALE
import com.kevalpatel2106.feature.log.usecase.ConvertToPaddedLogs
import com.kevalpatel2106.feature.log.usecase.LogSourceSelector
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection
import com.kevalpatel2106.repository.ProjectRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BuildLogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val projectRepo: ProjectRepo,
    private val logSourceSelector: LogSourceSelector,
    private val calculateTextScale: CalculateTextScale,
    private val displayErrorMapper: DisplayErrorMapper,
    private val convertToPaddedLogs: ConvertToPaddedLogs,
) : BaseViewModel<BuildLogVMEvent>() {
    private val navArgs = BuildLogFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _viewState = MutableStateFlow<BuildLogViewState>(BuildLogViewState.initialState())
    val viewState = _viewState.asStateFlow()

    init {
        downloadLogs()
    }

    private fun downloadLogs() {
        viewModelScope.launch {
            _viewState.emit(BuildLogViewState.Loading)
            runCatching {
                logSourceSelector(
                    accountId = navArgs.accountId.toAccountId(),
                    projectId = navArgs.projectId.toProjectId(),
                    buildId = navArgs.buildId.toBuildId(),
                    jobId = navArgs.jobId.toJobIdOrNull(),
                )
            }.onSuccess { logs ->
                if (logs.isBlank()) {
                    _viewState.emit(Empty)
                } else {
                    _viewState.emit(Success(convertToPaddedLogs(logs), DEFAULT_SCALE))
                    _vmEventsFlow.emit(ScrollToTop)
                }
            }.onFailure { error ->
                Timber.e(error)
                _viewState.emit(Error(error))
            }
        }
    }

    fun reload() = downloadLogs()

    fun scrollToBottom() = viewModelScope.launch { _vmEventsFlow.emit(ScrollToBottom) }

    fun close() = viewModelScope.launch { _vmEventsFlow.emit(Close) }

    fun scrollToTop() = viewModelScope.launch { _vmEventsFlow.emit(ScrollToTop) }

    fun onSaveLogs() = viewModelScope.launch {
        runCatching {
            val project = projectRepo.getProject(
                navArgs.projectId.toProjectId(),
                navArgs.accountId.toAccountId(),
            )
            project?.fullName ?: DEFAULT_LOG_FILE_NAME
        }.onSuccess { fileName ->
            _vmEventsFlow.emit(CreateLogFile("$fileName.txt", (viewState.value as Success).logs))
        }.onFailure { error ->
            Timber.e(error)
            _vmEventsFlow.emit(ErrorSavingLog(displayErrorMapper(error)))
        }
    }

    fun onTextSizeChanged(@TextChangeDirection direction: Int) = _viewState.modify(viewModelScope) {
        if (this is Success) {
            copy(textScale = calculateTextScale(direction))
        } else {
            this
        }
    }

    companion object {
        private const val DEFAULT_LOG_FILE_NAME = "build"
    }
}
