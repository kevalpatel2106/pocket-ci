package com.kevalpatel2106.core.paging

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.core.paging.FirstPageLoadState.Error
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loaded
import com.kevalpatel2106.core.paging.FirstPageLoadState.Loading
import com.kevalpatel2106.core.paging.usecase.LoadStateMapperImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.mock

internal class LoadStateMapperImplTest {
    private val adapter = mock<PagingDataAdapter<String, RecyclerView.ViewHolder>>()
    private val subject = LoadStateMapperImpl()

    @ParameterizedTest(name = "given refresh state error when firstPageLoadState then error returned")
    @MethodSource("provideValues")
    fun `given refresh state error when firstPageLoadState then error returned`(
        refresh: LoadState,
        appendState: LoadState,
        sourceRefresh: LoadState,
        firstPageLoadState: FirstPageLoadState,
    ) {
        val combinedLoadStates = CombinedLoadStates(
            refresh = refresh,
            append = appendState,
            prepend = LoadState.Loading,
            source = LoadStates(sourceRefresh, LoadState.Loading, LoadState.Loading),
        )

        val actual = subject.invoke(adapter, combinedLoadStates)

        assertEquals(firstPageLoadState, actual)
    }

    companion object {
        private val fixture = KFixture()
        private val error = IllegalAccessError()

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: refresh state, append end of pagination, source refresh states, is adapter empty, expected
            arguments(
                LoadState.Error(error),
                LoadState.NotLoading(true),
                LoadState.Loading,
                Error(error),
            ),
            arguments(
                LoadState.Error(error),
                LoadState.NotLoading(true),
                LoadState.Loading,
                Error(error),
            ),
            arguments(
                LoadState.Error(error),
                LoadState.NotLoading(true),
                LoadState.Error(error),
                Error(error),
            ),
            arguments(
                LoadState.Error(error),
                LoadState.NotLoading(true),
                LoadState.NotLoading(false),
                Error(error),
            ),
            arguments(
                LoadState.Error(error),
                LoadState.Loading,
                LoadState.Loading,
                Error(error),
            ),
            arguments(
                LoadState.NotLoading(fixture()),
                LoadState.Loading,
                LoadState.Loading,
                Loaded,
            ),
            arguments(
                LoadState.NotLoading(fixture()),
                LoadState.NotLoading(fixture()),
                LoadState.Loading,
                Loading,
            ),
        )
    }
}
