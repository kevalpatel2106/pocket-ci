package com.kevalpatel2106.pocketci.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.extentions.collectInFragment
import com.kevalpatel2106.pocketci.splash.usecase.HandleSplashVMEvents
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    internal lateinit var handleSplashVMEvents: HandleSplashVMEvents

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.collectInFragment(this, ::handleViewState)
        viewModel.vmEventsFlow.collectInFragment(this, handleSplashVMEvents::invoke)
    }

    private fun handleViewState(viewState: SplashViewState) {
        if (AppCompatDelegate.getDefaultNightMode() != viewState.nightMode.value) {
            AppCompatDelegate.setDefaultNightMode(viewState.nightMode.value)
        }
    }
}
