package com.kevalpatel2106.feature.drawer

internal sealed class BottomDrawerVMEvent {
    object OpenSettingsAndClose : BottomDrawerVMEvent()
    object OpenAccountsAndClose : BottomDrawerVMEvent()
}
