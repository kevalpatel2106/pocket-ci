package com.kevalpatel2106.pocketci.splash

import com.kevalpatel2106.entity.NightMode

data class SplashViewState(val nightMode: NightMode) {
    companion object {
        fun initialState() = SplashViewState(NightMode.AUTO)
    }
}
