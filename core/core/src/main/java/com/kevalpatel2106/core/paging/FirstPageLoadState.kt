package com.kevalpatel2106.core.paging

sealed class FirstPageLoadState {
    data class Error(val error: Throwable) : FirstPageLoadState()
    object Loading : FirstPageLoadState()
    object Empty : FirstPageLoadState()
    object Loaded : FirstPageLoadState()
}
