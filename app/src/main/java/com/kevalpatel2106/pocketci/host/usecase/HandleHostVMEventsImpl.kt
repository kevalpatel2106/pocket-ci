package com.kevalpatel2106.pocketci.host.usecase

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerDialog
import com.kevalpatel2106.pocketci.host.HostVMEvents
import com.kevalpatel2106.pocketci.host.HostVMEvents.NavigateUp
import com.kevalpatel2106.pocketci.host.HostVMEvents.ShowBottomDialog
import javax.inject.Inject

internal class HandleHostVMEventsImpl @Inject constructor(
    private val activity: Activity,
) : HandleHostVMEvents {

    override fun invoke(event: HostVMEvents, navController: NavController) {
        when (event) {
            ShowBottomDialog -> {
                BottomDrawerDialog.show((activity as AppCompatActivity).supportFragmentManager)
            }
            NavigateUp -> navController.navigateUp()
        }
    }
}
