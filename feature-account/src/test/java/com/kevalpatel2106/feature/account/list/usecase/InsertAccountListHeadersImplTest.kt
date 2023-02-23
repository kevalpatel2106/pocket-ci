package com.kevalpatel2106.feature.account.list.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.feature.account.getAccountItemFixture
import com.kevalpatel2106.feature.account.list.AccountsListItem
import com.kevalpatel2106.feature.account.list.AccountsListItem.AccountItem
import com.kevalpatel2106.feature.account.list.AccountsListItem.HeaderItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class InsertAccountListHeadersImplTest {

    @ParameterizedTest(name = "given before and after in the list when invoke then check {2} separator item added")
    @MethodSource("provideValues")
    fun `given before and after in the list when invoke then check correct separator item added`(
        before: AccountItem?,
        after: AccountItem?,
        expected: AccountsListItem?,
    ) {
        val result = InsertAccountListHeadersImpl()(before, after)

        assertEquals(
            expected,
            result,
            "Inputs: Before ${before?.account?.type} and After: ${after?.account?.type}",
        )
    }

    companion object {
        private val kFixture = KFixture()
        private val bitriseAccountItemFixture = getAccountItemFixture(kFixture)
            .copy(account = getAccountFixture(kFixture).copy(type = CIType.BITRISE))
        private val githubAccountItemFixture = getAccountItemFixture(kFixture)
            .copy(account = getAccountFixture(kFixture).copy(type = CIType.GITHUB))

        @Suppress("unused")
        @JvmStatic
        fun provideValues() = listOf(
            // Format: before, after, expected separator
            arguments(null, null, null),
            arguments(githubAccountItemFixture, null, null),
            arguments(
                null,
                bitriseAccountItemFixture,
                HeaderItem(bitriseAccountItemFixture.ciName),
            ),
            arguments(
                bitriseAccountItemFixture,
                bitriseAccountItemFixture,
                null,
            ),
            arguments(
                githubAccountItemFixture,
                bitriseAccountItemFixture,
                HeaderItem(bitriseAccountItemFixture.ciName),
            ),
        )
    }
}
