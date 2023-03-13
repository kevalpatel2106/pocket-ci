package com.kevalpatel2106.feature.splash.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kevalpatel2106.core.extentions.collectStateInFragment
import com.kevalpatel2106.core.extentions.collectVMEventInFragment
import com.kevalpatel2106.feature.splash.splash.eventHandler.SplashVMEventHandler
import com.kevalpatel2106.feature.splash.splash.eventHandler.SplashViewStatesEventHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    internal lateinit var vMEventHandler: SplashVMEventHandler

    @Inject
    internal lateinit var handleViewStates: SplashViewStatesEventHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.collectStateInFragment(this, handleViewStates::invoke)
        viewModel.vmEventsFlow.collectVMEventInFragment(this, vMEventHandler::invoke)
    }
}
