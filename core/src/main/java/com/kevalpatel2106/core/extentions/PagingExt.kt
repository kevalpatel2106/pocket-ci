package com.kevalpatel2106.core.extentions

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingDataAdapter

fun LoadStates.isEmptyList(adapter: PagingDataAdapter<*, *>): Boolean {
    return refresh is LoadState.NotLoading &&
            append.endOfPaginationReached &&
            adapter.itemCount < 1
}
