package com.kevalpatel2106.pocketci.bottomDrawer

import androidx.lifecycle.viewModelScope
import com.kevalpatel2106.core.BaseViewModel
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenSettingsAndClose
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BottomDrawerViewModel @Inject constructor() : BaseViewModel<BottomDrawerVMEvent>() {

    fun onSettingsClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenSettingsAndClose) }
    }

    fun onAccountsClicked() {
        viewModelScope.launch { _vmEventsFlow.emit(OpenAccountsAndClose) }
    }
}
