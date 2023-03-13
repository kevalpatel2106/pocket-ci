package com.kevalpatel2106.feature.drawer.drawer.model

internal sealed class BottomDrawerVMEvent {
    object OpenSettingsAndClose : BottomDrawerVMEvent()
    object OpenAccountsAndClose : BottomDrawerVMEvent()
}
