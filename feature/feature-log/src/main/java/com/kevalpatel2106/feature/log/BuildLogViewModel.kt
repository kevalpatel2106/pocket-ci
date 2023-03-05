package com.kevalpatel2106.feature.log

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.errorHandling.DisplayErrorMapper
import com.kevalpatel2106.entity.id.toAccountId
import com.kevalpatel2106.entity.id.toBuildId
import com.kevalpatel2106.entity.id.toJobIdOrNull
import com.kevalpatel2106.entity.id.toProjectId
import com.kevalpatel2106.feature.log.BuildLogVMEvent.Close
import com.kevalpatel2106.feature.log.BuildLogVMEvent.CreateLogFile
import com.kevalpatel2106.feature.log.BuildLogVMEvent.ErrorSavingLog
import com.kevalpatel2106.feature.log.BuildLogVMEvent.InvalidateOptionsMenu
import com.kevalpatel2106.feature.log.BuildLogViewState.Empty
import com.kevalpatel2106.feature.log.BuildLogViewState.Error
import com.kevalpatel2106.feature.log.BuildLogViewState.Success
import com.kevalpatel2106.feature.log.menu.BuildMenuProviderCallback
import com.kevalpatel2106.feature.log.usecase.CalculateTextScale
import com.kevalpatel2106.feature.log.usecase.CalculateTextScale.Companion.DEFAULT_SCALE
import com.kevalpatel2106.feature.log.usecase.ConvertToPaddedLogs
import com.kevalpatel2106.feature.log.usecase.LogSourceSelector
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection
import com.kevalpatel2106.repository.ProjectRepo
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
internal class BuildLogViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val projectRepo: ProjectRepo,
    private val logSourceSelector: LogSourceSelector,
    private val calculateTextScale: CalculateTextScale,
    private val displayErrorMapper: DisplayErrorMapper,
    private val convertToPaddedLogs: ConvertToPaddedLogs,
) : ViewModel(), BuildMenuProviderCallback {
    private val navArgs = BuildLogFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _vmEventsFlow = MutableSharedFlow<BuildLogVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    private val _viewState = MutableStateFlow<BuildLogViewState>(BuildLogViewState.initialState())
    val viewState = _viewState.asStateFlow()

    init {
        downloadLogs()
    }

    private fun downloadLogs() {
        viewModelScope.launch {
            _viewState.emit(BuildLogViewState.Loading)
            runCatching {
                val logs = logSourceSelector(
                    accountId = navArgs.accountId.toAccountId(),
                    projectId = navArgs.projectId.toProjectId(),
                    buildId = navArgs.buildId.toBuildId(),
                    jobId = navArgs.jobId.toJobIdOrNull(),
                )
                convertToPaddedLogs(logs)
            }.onSuccess { logs ->
                if (logs.isEmpty()) {
                    _viewState.emit(Empty)
                } else {
                    _viewState.emit(Success(logs, DEFAULT_SCALE))
                }
            }.onFailure { error ->
                Timber.e(error)
                _viewState.emit(Error(displayErrorMapper(error)))
            }
            _vmEventsFlow.emit(InvalidateOptionsMenu)
        }
    }

    fun reload() = downloadLogs()

    fun close() = viewModelScope.launch { _vmEventsFlow.emit(Close) }

    override fun onSaveLogs() {
        viewModelScope.launch {
            runCatching {
                val project = projectRepo.getProject(
                    remoteId = navArgs.projectId.toProjectId(),
                    accountId = navArgs.accountId.toAccountId(),
                )
                project?.fullName ?: DEFAULT_LOG_FILE_NAME
            }.onSuccess { fileName ->
                _vmEventsFlow.emit(
                    CreateLogFile(
                        fileName = "$fileName.txt",
                        logs = (viewState.value as Success).logs.joinToString("\n"),
                    ),
                )
            }.onFailure { error ->
                Timber.e(error)
                _vmEventsFlow.emit(ErrorSavingLog(displayErrorMapper(error)))
            }
            _vmEventsFlow.emit(InvalidateOptionsMenu)
        }
    }

    override fun shouldShowMenu() = viewState.value is Success

    override fun onTextSizeChanged(@TextChangeDirection direction: Int) = _viewState.update {
        if (it is Success) {
            it.copy(textScale = calculateTextScale(direction))
        } else {
            it
        }
    }

    companion object {
        private const val DEFAULT_LOG_FILE_NAME = "build"
    }
}
