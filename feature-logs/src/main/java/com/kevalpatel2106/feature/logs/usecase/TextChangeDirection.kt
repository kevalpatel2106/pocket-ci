package com.kevalpatel2106.feature.logs.usecase

import androidx.annotation.IntDef
import com.kevalpatel2106.feature.logs.usecase.TextChangeDirection.Companion.TEXT_SIZE_DECREASE
import com.kevalpatel2106.feature.logs.usecase.TextChangeDirection.Companion.TEXT_SIZE_INCREASE

@IntDef(TEXT_SIZE_DECREASE, TEXT_SIZE_INCREASE)
internal annotation class TextChangeDirection {

    companion object {
        const val TEXT_SIZE_DECREASE = 0
        const val TEXT_SIZE_INCREASE = 1
    }
}
