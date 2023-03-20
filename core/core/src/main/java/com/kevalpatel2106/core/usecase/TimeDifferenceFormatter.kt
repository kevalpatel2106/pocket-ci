package com.kevalpatel2106.core.usecase

import java.util.Date

interface TimeDifferenceFormatter {

    operator fun invoke(
        dateStart: Date,
        dateEnd: Date,
        appendText: String? = null,
        showMorePrecise: Boolean,
    ): String
}
