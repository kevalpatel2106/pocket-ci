package com.kevalpatel2106.registration.ciSelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.entity.analytics.ClickEvent
import com.kevalpatel2106.registration.ciSelection.CISelectionVMEvent.Close
import com.kevalpatel2106.registration.ciSelection.CISelectionVMEvent.OpenRegisterAccount
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.Companion.initialState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.ErrorState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.SuccessState
import com.kevalpatel2106.repository.AnalyticsRepo
import com.kevalpatel2106.repository.CIInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CISelectionViewModel @Inject constructor(
    private val selectionRepo: CIInfoRepo,
    private val analyticsRepo: AnalyticsRepo,
) : ViewModel() {

    private val _viewState = MutableStateFlow<CISelectionViewState>(initialState())
    val viewState = _viewState.asStateFlow()

    private val _vmEventsFlow = MutableSharedFlow<CISelectionVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    init {
        loadSupportedCIInfo()
    }

    private fun loadSupportedCIInfo() = viewModelScope.launch {
        runCatching { selectionRepo.getSupportedCI() }
            .onFailure { error ->
                Timber.e(error)
                _viewState.value = ErrorState(error)
            }
            .onSuccess { list -> _viewState.value = SuccessState(list) }
    }

    fun onCISelected(ci: CIInfo) {
        analyticsRepo.sendEvent(ClickEvent(ClickEvent.Action.CI_SELECTED, ci.type.name))
        viewModelScope.launch { _vmEventsFlow.emit(OpenRegisterAccount(ci)) }
    }

    fun close() = viewModelScope.launch { _vmEventsFlow.emit(Close) }

    fun reload() = loadSupportedCIInfo()
}
