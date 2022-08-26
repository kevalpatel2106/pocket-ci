package com.kevalpatel2106.feature.log.menu

import com.kevalpatel2106.feature.log.usecase.TextChangeDirection

internal interface BuildMenuProviderCallback {
    fun shouldShowMenu(): Boolean
    fun onTextSizeChanged(@TextChangeDirection direction: Int)
    fun onSaveLogs()
}
