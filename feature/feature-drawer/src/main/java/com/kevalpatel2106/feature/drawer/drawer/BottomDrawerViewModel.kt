package com.kevalpatel2106.feature.drawer.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerItem
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerItem.LinkedAccounts
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerItem.Settings
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent.OpenSettingsAndClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BottomDrawerViewModel @Inject constructor() : ViewModel(), BottomDrawerViewEvents {

    private val _viewStateFlow = MutableStateFlow(
        listOf(
            LinkedAccounts,
            Settings,
        ),
    )
    val viewStateFlow = _viewStateFlow.asStateFlow()

    private val _vmEventsFlow = MutableSharedFlow<BottomDrawerVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    override fun onDrawerItemClicked(item: BottomDrawerItem) {
        when (item) {
            Settings -> viewModelScope.launch { _vmEventsFlow.emit(OpenSettingsAndClose) }
            LinkedAccounts -> viewModelScope.launch { _vmEventsFlow.emit(OpenAccountsAndClose) }
        }
    }
}
