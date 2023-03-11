package com.kevalpatel2106.feature.splash.usecase

import com.kevalpatel2106.feature.splash.SplashVMEvent

internal interface HandleSplashVMEvents {
    operator fun invoke(event: SplashVMEvent)
}
