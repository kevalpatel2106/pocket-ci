package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccountId(private val value: Long) : Parcelable {

    init {
        assert(value >= 0) { "Account id cannot be negative!" }
    }

    fun getValue() = value

    companion object {
        val EMPTY = AccountId(0L)
    }
}

fun Long?.toAccountIdOrNull() = if (this != null) AccountId(this) else null
fun Long.toAccountId() = AccountId(this)
