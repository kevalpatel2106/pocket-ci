package com.kevalpatel2106.coreViews.useCase

import java.util.Date

internal interface CalculateTickInterval {
    operator fun invoke(pastDate: Date, showMorePrecise: Boolean, now: Date): Long
}
