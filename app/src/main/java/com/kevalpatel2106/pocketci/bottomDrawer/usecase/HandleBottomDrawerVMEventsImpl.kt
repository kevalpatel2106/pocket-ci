package com.kevalpatel2106.pocketci.bottomDrawer.usecase

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent.OpenSettingsAndClose
import javax.inject.Inject

internal class HandleBottomDrawerVMEventsImpl @Inject constructor(
    private val fragment: Fragment,
) : HandleBottomDrawerVMEvents {

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
