package com.kevalpatel2106.entity

data class PagedData<T>(val data: List<T>, val nextCursor: String?)
