package com.kevalpatel2106.feature.drawer.drawer.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent.OpenSettingsAndClose
import javax.inject.Inject

internal class BottomDrawerVMEventHandlerImpl @Inject constructor(
    private val fragment: Fragment,
) : BottomDrawerVMEventHandler {

    override fun invoke(event: BottomDrawerVMEvent): Unit = with(fragment) {
        when (event) {
            OpenAccountsAndClose -> {
                findNavController().navigateToInAppDeeplink(DeepLinkDestinations.AccountList)
                (fragment as BottomSheetDialogFragment).dismiss()
            }
            OpenSettingsAndClose -> {
                findNavController().navigateToInAppDeeplink(DeepLinkDestinations.SettingList)
                (fragment as BottomSheetDialogFragment).dismiss()
            }
        }
    }
}
