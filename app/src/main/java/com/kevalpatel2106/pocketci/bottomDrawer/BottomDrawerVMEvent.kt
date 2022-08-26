package com.kevalpatel2106.pocketci.bottomDrawer

internal sealed class BottomDrawerVMEvent {
    object OpenSettingsAndClose : BottomDrawerVMEvent()
    object OpenAccountsAndClose : BottomDrawerVMEvent()
}
