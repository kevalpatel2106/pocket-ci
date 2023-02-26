package com.kevalpatel2106.coreViews.useCase

import java.util.Date

internal interface TimeDifferenceFormatter {

    operator fun invoke(
        dateStart: Date,
        dateEnd: Date,
        appendText: String?,
        showMorePrecise: Boolean,
    ): String
}
