package com.kevalpatel2106.connector.ci.entity

data class PagedData<T>(val data: List<T>, val nextCursor: String?)
