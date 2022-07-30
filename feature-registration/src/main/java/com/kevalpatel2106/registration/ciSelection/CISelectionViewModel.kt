package com.kevalpatel2106.registration.ciSelection

import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.entity.CIInfo
import com.kevalpatel2106.registration.ciSelection.CISelectionVMEvent.OpenRegisterAccount
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.Companion.initialState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.ErrorState
import com.kevalpatel2106.registration.ciSelection.CISelectionViewState.SuccessState
import com.kevalpatel2106.registration.ciSelection.adapter.CISelectionAdapterCallback
import com.kevalpatel2106.repository.CIInfoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CISelectionViewModel @Inject constructor(
    private val selectionRepo: CIInfoRepo,
) : BaseViewModel<CISelectionVMEvent>(), CISelectionAdapterCallback {

    private val _viewState = MutableStateFlow<CISelectionViewState>(initialState())
    val viewState = _viewState.asStateFlow()

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

    override fun onCISelected(ci: CIInfo) {
        viewModelScope.launch { _vmEventsFlow.emit(OpenRegisterAccount(ci)) }
    }

    fun reload() = loadSupportedCIInfo()
}
