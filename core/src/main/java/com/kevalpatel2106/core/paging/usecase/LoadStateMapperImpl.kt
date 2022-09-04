package com.kevalpatel2106.core.paging.usecase

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingDataAdapter
import com.kevalpatel2106.core.paging.FirstPageLoadState
import timber.log.Timber
import javax.inject.Inject

internal class LoadStateMapperImpl @Inject constructor() : LoadStateMapper {

    override operator fun invoke(
        adapter: PagingDataAdapter<*, *>,
        loadState: CombinedLoadStates,
    ): FirstPageLoadState {
        val refreshStates = loadState.refresh
        return when {
            refreshStates is LoadState.Error -> {
                Timber.e(refreshStates.error)
                FirstPageLoadState.Error(refreshStates.error)
            }
            loadState.isLoadingFirstPage() -> FirstPageLoadState.Loading
            loadState.source.isEmptyList(adapter) -> FirstPageLoadState.Empty
            else -> FirstPageLoadState.Loaded
        }
    }

    private fun LoadStates.isEmptyList(adapter: PagingDataAdapter<*, *>): Boolean {
        return refresh is LoadState.NotLoading &&
                append.endOfPaginationReached &&
                adapter.itemCount < 1
    }

    private fun CombinedLoadStates.isLoadingFirstPage(): Boolean {
        return source.refresh is LoadState.Loading && append !is LoadState.Loading
    }
}
