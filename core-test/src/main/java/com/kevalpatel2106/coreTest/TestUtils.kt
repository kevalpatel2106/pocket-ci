package com.kevalpatel2106.coreTest

import java.util.Calendar
import java.util.Date

fun Calendar.toDate() = Date(timeInMillis)

fun Calendar.minus(value: Int, field: Int): Calendar = Calendar.getInstance().also {
    it.timeInMillis = this.timeInMillis
    it.set(field, this.get(field) - value)
}

fun Calendar.plus(value: Int, field: Int): Calendar = Calendar.getInstance().also {
    it.timeInMillis = this.timeInMillis
    it.set(field, this.get(field) + value)
}
