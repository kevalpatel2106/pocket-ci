package com.kevalpatel2106.pocketci.host

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.core.extentions.modify
import com.kevalpatel2106.pocketci.R
import com.kevalpatel2106.pocketci.host.AppNavigationConfig.SCREENS_WITH_BOTTOM_DRAWER
import com.kevalpatel2106.pocketci.host.AppNavigationConfig.SCREENS_WITH_NO_TOOLBAR
import com.kevalpatel2106.repository.AnalyticsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class HostViewModel @Inject constructor(
    private val analyticsRepo: AnalyticsRepo,
) : BaseViewModel<HostVMEvents>() {

    private val _viewState = MutableStateFlow(HostViewState.initialState())
    val viewState = _viewState.asStateFlow()

    fun onNavDestinationChanged(
        previousDstId: Int?,
        previousDst: String?,
        newDstId: Int,
        nextDst: String?,
        arguments: Bundle?,
    ) {
        Timber.i("NavTracker: $previousDst to $nextDst: $arguments")
        analyticsRepo.sendScreenNavigation(nextDst)

        val navigationIcon = when {
            newDstId in SCREENS_WITH_BOTTOM_DRAWER -> R.drawable.ic_hamburger
            previousDstId == null -> null
            else -> R.drawable.ic_arrow_back
        }
        val isNavigationIconVisible = newDstId !in SCREENS_WITH_NO_TOOLBAR

        _viewState.modify(viewModelScope) {
            copy(
                navigationIcon = navigationIcon,
                navigationIconVisible = isNavigationIconVisible,
            )
        }
    }

    fun onNavigateUpClicked(currentDestination: Int?) = viewModelScope.launch {
        when (currentDestination) {
            in SCREENS_WITH_BOTTOM_DRAWER -> _vmEventsFlow.emit(HostVMEvents.ShowBottomDialog)
            else -> _vmEventsFlow.emit(HostVMEvents.NavigateUp)
        }
    }
}
