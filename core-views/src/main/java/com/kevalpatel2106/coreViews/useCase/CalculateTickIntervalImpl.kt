package com.kevalpatel2106.coreViews.useCase

import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class CalculateTickIntervalImpl @Inject constructor() : CalculateTickInterval {

    override operator fun invoke(pastDate: Date, showMorePrecise: Boolean, now: Date): Long {
        val difference = now.time - pastDate.time
        require(difference > 0) { "$pastDate cannot be in the future." }
        return if (showMorePrecise) morePreciseTick(difference) else lessPreciseTick(difference)
    }

    private fun lessPreciseTick(difference: Long) = when {
        difference < TICK_EVERY_MINUTE -> TICK_EVERY_SECOND
        difference < TICK_EVERY_HOUR -> TICK_EVERY_MINUTE
        else -> TICK_EVERY_HOUR
    }

    private fun morePreciseTick(difference: Long) = when {
        difference < TICK_EVERY_MINUTE -> TICK_EVERY_SECOND
        difference < TICK_EVERY_HOUR -> TICK_EVERY_SECOND
        difference < TICK_EVERY_DAY -> TICK_EVERY_MINUTE
        else -> TICK_EVERY_HOUR
    }

    companion object {
        private val TICK_EVERY_SECOND = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS)
        private val TICK_EVERY_MINUTE = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
        private val TICK_EVERY_HOUR = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        private val TICK_EVERY_DAY = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
    }
}
