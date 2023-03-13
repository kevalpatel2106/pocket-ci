package com.kevalpatel2106.feature.splash.splash.eventHandler

import com.kevalpatel2106.feature.splash.splash.model.SplashViewState

internal interface SplashViewStatesEventHandler {
    operator fun invoke(viewState: SplashViewState)
}
