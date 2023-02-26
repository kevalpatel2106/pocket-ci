package com.kevalpatel2106.feature.log.usecase

import com.kevalpatel2106.feature.log.usecase.CalculateTextScale.Companion.DEFAULT_SCALE
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection.Companion.TEXT_SIZE_DECREASE
import com.kevalpatel2106.feature.log.usecase.TextChangeDirection.Companion.TEXT_SIZE_INCREASE
import javax.inject.Inject

internal class CalculateTextScaleImpl @Inject constructor() : CalculateTextScale {

    override fun invoke(@TextChangeDirection direction: Int): Float {
        return when (direction) {
            TEXT_SIZE_INCREASE -> DOUBLE_SCALE
            TEXT_SIZE_DECREASE -> HALF_SCALE
            else -> DEFAULT_SCALE
        }
    }

    companion object {
        private const val HALF_SCALE = 0.5f
        private const val DOUBLE_SCALE = 2f
    }
}
