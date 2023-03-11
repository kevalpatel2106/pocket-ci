package com.kevalpatel2106.feature.splash.usecase

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.splash.SplashVMEvent
import com.kevalpatel2106.feature.splash.SplashVMEvent.CloseApplication
import com.kevalpatel2106.feature.splash.SplashVMEvent.ErrorLoadingTheme
import com.kevalpatel2106.feature.splash.SplashVMEvent.OpenProjects
import com.kevalpatel2106.feature.splash.SplashVMEvent.OpenRegisterAccount
import javax.inject.Inject

internal class HandleSplashVMEventsImpl @Inject constructor(
    private val fragment: Fragment,
) : HandleSplashVMEvents {

    override fun invoke(event: SplashVMEvent): Unit = with(fragment) {
        when (event) {
            CloseApplication -> activity?.finish()
            is OpenProjects -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.ProjectList(event.accountId),
                cleanUpStack = true,
            )
            OpenRegisterAccount -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.CiSelection,
                cleanUpStack = true,
            )
            ErrorLoadingTheme -> Toast.makeText(
                requireContext(),
                R.string.splash_error_loading_theme,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}
