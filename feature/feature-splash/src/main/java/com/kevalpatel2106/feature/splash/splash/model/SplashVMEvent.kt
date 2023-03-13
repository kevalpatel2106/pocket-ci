package com.kevalpatel2106.feature.splash.splash.model

import com.kevalpatel2106.entity.id.AccountId

internal sealed class SplashVMEvent {
    data class OpenProjects(val accountId: AccountId) : SplashVMEvent()
    object OpenRegisterAccount : SplashVMEvent()
    object CloseApplication : SplashVMEvent()
    object ErrorLoadingTheme : SplashVMEvent()
}
