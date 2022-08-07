package com.kevalpatel2106.coreViews.useCase

import java.util.Calendar
import java.util.Date

internal fun Calendar.toDate() = Date(timeInMillis)

internal fun Calendar.minus(value: Int, field: Int) = Calendar.getInstance().also {
    it.timeInMillis = this.timeInMillis
    it.set(field, this.get(field) - value)
}

internal fun Calendar.plus(value: Int, field: Int) = Calendar.getInstance().also {
    it.timeInMillis = this.timeInMillis
    it.set(field, this.get(field) + value)
}
