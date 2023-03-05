package com.kevalpatel2106.pocketci.bottomDrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenSettingsAndClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BottomDrawerViewModel @Inject constructor() : ViewModel(), BottomDrawerViewEvents {

    private val _vmEventsFlow = MutableSharedFlow<BottomDrawerVMEvent>()
    val vmEventsFlow = _vmEventsFlow.asSharedFlow()

    override fun onSettingsClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenSettingsAndClose) }
    }

    override fun onAccountsClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenAccountsAndClose) }
    }
}
