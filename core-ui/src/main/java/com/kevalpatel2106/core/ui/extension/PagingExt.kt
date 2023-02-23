package com.kevalpatel2106.core.ui.extension

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

fun <T : Any> LazyPagingItems<T>.isLoadingFirstPage() = loadState.refresh is LoadState.Loading

fun <T : Any> LazyPagingItems<T>.isErrorInFirstPage() = loadState.refresh is LoadState.Error

fun <T : Any> LazyPagingItems<T>.isLoadingNextPage() = loadState.append is LoadState.Loading

fun <T : Any> LazyPagingItems<T>.isErrorLoadingNextPage() = loadState.append is LoadState.Error

fun <T : Any> LazyPagingItems<T>.isEmptyList() = loadState.refresh is LoadState.NotLoading &&
    loadState.append.endOfPaginationReached &&
    itemCount < 1
