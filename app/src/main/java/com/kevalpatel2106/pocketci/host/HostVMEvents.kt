package com.kevalpatel2106.pocketci.host

internal sealed class HostVMEvents {
    object ShowBottomDialog : HostVMEvents()
    object NavigateUp : HostVMEvents()
}
