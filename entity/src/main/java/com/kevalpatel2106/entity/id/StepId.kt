package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StepId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Step ID cannot be blank value!" }
    }

    fun getValue() = value
}
