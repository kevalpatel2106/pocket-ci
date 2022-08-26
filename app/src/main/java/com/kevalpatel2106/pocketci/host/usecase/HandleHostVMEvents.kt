package com.kevalpatel2106.pocketci.host.usecase

import androidx.navigation.NavController
import com.kevalpatel2106.pocketci.host.HostVMEvents

internal interface HandleHostVMEvents {
    operator fun invoke(event: HostVMEvents, navController: NavController)
}
