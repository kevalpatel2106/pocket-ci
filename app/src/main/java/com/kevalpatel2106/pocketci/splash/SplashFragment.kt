package com.kevalpatel2106.pocketci.splash

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.pocketci.R
import com.kevalpatel2106.pocketci.splash.SplashVMEvent.CloseApplication
import com.kevalpatel2106.pocketci.splash.SplashVMEvent.ErrorLoadingTheme
import com.kevalpatel2106.pocketci.splash.SplashVMEvent.OpenProjects
import com.kevalpatel2106.pocketci.splash.SplashVMEvent.OpenRegisterAccount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, ::handleSingleViewState)
    }

    private fun handleViewState(viewState: SplashViewState) {
        if (AppCompatDelegate.getDefaultNightMode() != viewState.nightMode.value) {
            AppCompatDelegate.setDefaultNightMode(viewState.nightMode.value)
        }
    }

    private fun handleSingleViewState(event: SplashVMEvent) = when (event) {
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
