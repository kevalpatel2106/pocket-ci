package com.kevalpatel2106.pocketci.host

sealed class HostVMEvents {
    object ShowBottomDialog : HostVMEvents()
    object NavigateUp : HostVMEvents()
}
