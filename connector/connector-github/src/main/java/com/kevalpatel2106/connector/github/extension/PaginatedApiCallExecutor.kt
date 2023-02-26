package com.kevalpatel2106.connector.github.extension

import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.entity.PagedData

internal suspend inline fun <D : Any, E : Any> executePaginatedApiCall(
    currentCursor: String?,
    crossinline executeApiCall: suspend (page: Int) -> D,
    crossinline pagedDataMapper: (dto: D) -> List<E>,
): PagedData<E> {
    // fetch
    val pageNumber = currentCursor?.toIntOrNull() ?: GitHubEndpoint.FIRST_PAGE_CURSOR
    val pagingListDto = executeApiCall(pageNumber)

    // map
    val mappedList = pagedDataMapper(pagingListDto)

    // build paged data
    val nextCursor = if (mappedList.isEmpty()) null else pageNumber + 1
    return PagedData(data = mappedList, nextCursor = nextCursor?.toString())
}
