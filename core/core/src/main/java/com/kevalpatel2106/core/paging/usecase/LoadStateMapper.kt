package com.kevalpatel2106.core.paging.usecase

import androidx.paging.CombinedLoadStates
import androidx.paging.PagingDataAdapter
import com.kevalpatel2106.core.paging.FirstPageLoadState

interface LoadStateMapper {
    operator fun invoke(
        adapter: PagingDataAdapter<*, *>,
        loadState: CombinedLoadStates,
    ): FirstPageLoadState
}
