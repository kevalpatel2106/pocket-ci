package com.kevalpatel2106.core.usecase

import android.app.Application
import com.kevalpatel2106.core.resources.R
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class TimeDifferenceFormatterImpl @Inject constructor(
    application: Application,
) : TimeDifferenceFormatter {

    private val resource = application.resources

    override operator fun invoke(
        dateStart: Date,
        dateEnd: Date,
        appendText: String?,
        showMorePrecise: Boolean,
    ): String {
        val difference = TimeUnit.MILLISECONDS.toSeconds(dateEnd.time - dateStart.time)
        require(difference >= 0) { "$dateStart cannot be after $dateEnd." }

        return if (showMorePrecise) {
            morePrecise(difference, appendText.orEmpty())
        } else {
            lessPrecise(difference, appendText.orEmpty())
        }.trim()
    }

    private fun morePrecise(differenceSeconds: Long, appendText: String) = when {
        differenceSeconds < MINUTE -> {
            resource.getString(
                R.string.time_format_second_short,
                differenceSeconds.toInt(),
                appendText,
            )
        }
        differenceSeconds < HOUR -> {
            val minutes = differenceSeconds / MINUTE
            val seconds = differenceSeconds - (minutes * MINUTE)
            resource.getString(
                R.string.time_format_minute_second,
                minutes.toInt(),
                seconds.toInt(),
                appendText,
            )
        }
        differenceSeconds < DAY -> {
            val hours = differenceSeconds / HOUR
            val minutes = (differenceSeconds - (hours * HOUR)) / MINUTE
            resource.getString(
                R.string.time_format_hour_minute,
                hours.toInt(),
                minutes.toInt(),
                appendText,
            )
        }
        differenceSeconds < WEEK -> {
            val days = differenceSeconds / DAY
            val hours = (differenceSeconds - (days * DAY)) / HOUR
            resource.getString(
                R.string.time_format_day_hour,
                days.toInt(),
                hours.toInt(),
                appendText,
            )
        }
        else -> {
            resource.getQuantityString(
                R.plurals.time_format_week,
                (differenceSeconds / WEEK).toInt(),
                (differenceSeconds / WEEK).toInt(),
                appendText,
            )
        }
    }

    private fun lessPrecise(differenceSeconds: Long, appendText: String) = when {
        differenceSeconds < MINUTE -> {
            val seconds = differenceSeconds.toInt()
            resource.getQuantityString(R.plurals.time_format_second, seconds, seconds, appendText)
        }
        differenceSeconds < HOUR -> {
            val minutes = (differenceSeconds / MINUTE).toInt()
            resource.getQuantityString(R.plurals.time_format_minute, minutes, minutes, appendText)
        }
        differenceSeconds < DAY -> {
            val hours = (differenceSeconds / HOUR).toInt()
            resource.getQuantityString(R.plurals.time_format_hour, hours, hours, appendText)
        }
        differenceSeconds < WEEK -> {
            val days = (differenceSeconds / DAY).toInt()
            resource.getQuantityString(R.plurals.time_format_day, days, days, appendText)
        }
        differenceSeconds < YEAR -> {
            val weeks = (differenceSeconds / WEEK).toInt()
            resource.getQuantityString(R.plurals.time_format_week, weeks, weeks, appendText)
        }
        else -> {
            val years = (differenceSeconds / YEAR).toInt()
            resource.getQuantityString(R.plurals.time_format_year, years, years, appendText)
        }
    }

    companion object {
        private val MINUTE = TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES)
        private val HOUR = TimeUnit.SECONDS.convert(1, TimeUnit.HOURS)
        private val DAY = TimeUnit.SECONDS.convert(1, TimeUnit.DAYS)
        private val WEEK = TimeUnit.SECONDS.convert(7, TimeUnit.DAYS)
        private val YEAR = TimeUnit.SECONDS.convert(365, TimeUnit.DAYS)
    }
}
