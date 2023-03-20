package com.kevalpatel2106.feature.splash.splash.eventHandler

import com.kevalpatel2106.feature.splash.splash.model.SplashVMEvent

internal interface SplashVMEventHandler {
    operator fun invoke(event: SplashVMEvent)
}
