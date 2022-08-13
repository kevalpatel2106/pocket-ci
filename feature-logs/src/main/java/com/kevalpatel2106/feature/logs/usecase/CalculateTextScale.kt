package com.kevalpatel2106.feature.logs.usecase

internal interface CalculateTextScale {

    operator fun invoke(@TextChangeDirection direction: Int): Float

    companion object {
        const val DEFAULT_SCALE = 1f
    }
}
