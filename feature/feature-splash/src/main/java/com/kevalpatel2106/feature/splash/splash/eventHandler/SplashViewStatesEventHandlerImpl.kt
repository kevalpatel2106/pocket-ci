package com.kevalpatel2106.feature.splash.splash.eventHandler

import androidx.appcompat.app.AppCompatDelegate
import com.kevalpatel2106.feature.splash.splash.model.SplashViewState
import javax.inject.Inject

internal class SplashViewStatesEventHandlerImpl @Inject constructor() :
    SplashViewStatesEventHandler {

    override fun invoke(viewState: SplashViewState) {
        if (AppCompatDelegate.getDefaultNightMode() != viewState.nightMode.value) {
            AppCompatDelegate.setDefaultNightMode(viewState.nightMode.value)
        }
    }
}
