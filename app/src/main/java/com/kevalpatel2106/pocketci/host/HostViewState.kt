package com.kevalpatel2106.pocketci.host

import androidx.annotation.DrawableRes

data class HostViewState(
    val navigationIconVisible: Boolean,
    @DrawableRes val navigationIcon: Int?,
) {
    companion object {
        fun initialState() = HostViewState(
            navigationIconVisible = false,
            navigationIcon = null,
        )
    }
}
