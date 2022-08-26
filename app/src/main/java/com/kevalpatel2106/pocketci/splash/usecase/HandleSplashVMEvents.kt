package com.kevalpatel2106.pocketci.splash.usecase

import com.kevalpatel2106.pocketci.splash.SplashVMEvent

internal interface HandleSplashVMEvents {
    operator fun invoke(event: SplashVMEvent)
}
