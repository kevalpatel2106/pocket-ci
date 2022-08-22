package com.kevalpatel2106.feature.account.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.entity.id.AccountId
import com.kevalpatel2106.feature.account.getAccountItemFixture
import com.kevalpatel2106.feature.account.list.adapter.AccountsListItem.HeaderItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AccountsDiffCallbackTest {

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are contents same then return {2}")
    @MethodSource("provideAreContentSame")
    fun `given old and new item when checking are contents same then return expected value`(
        oldItem: AccountsListItem,
        newItem: AccountsListItem,
        expectedAreContentSame: Boolean,
    ) {
        val actual = AccountsDiffCallback.areContentsTheSame(oldItem, newItem)

        assertEquals(expectedAreContentSame, actual)
    }

    @ParameterizedTest(name = "given old {0} and new {1} item when checking are items same then return {2}")
    @MethodSource("provideAreItemSame")
    fun `given old and new item when checking are items same then return expected value`(
        oldItem: AccountsListItem,
        newItem: AccountsListItem,
        expectedAreItemSame: Boolean,
    ) {
        val actual = AccountsDiffCallback.areItemsTheSame(oldItem, newItem)

        assertEquals(expectedAreItemSame, actual)
    }

    companion object {
        private val fixture = KFixture()
        private val account = getAccountFixture(fixture)
        private val accountItem = getAccountItemFixture(fixture).copy(account = account)
        private val headerItem = fixture<HeaderItem>()

        @Suppress("unused")
        @JvmStatic
        fun provideAreContentSame() = listOf(
            // Format old item, new item, expected are content same
            arguments(accountItem, accountItem.copy(), true),
            arguments(headerItem, headerItem.copy(), true),
            arguments(accountItem, headerItem, false),
            arguments(accountItem.copy(ciIcon = 11), accountItem.copy(ciIcon = 10), false),
            arguments(
                accountItem.copy(account = account.copy(name = "1")),
                accountItem.copy(account = account.copy(name = "2")),
                false,
            ),
            arguments(headerItem.copy(ciName = 11), headerItem.copy(ciName = 10), false),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideAreItemSame() = listOf(
            // Format old item, new item, expected are item same
            arguments(accountItem, accountItem.copy(), true),
            arguments(headerItem, headerItem.copy(), true),
            arguments(accountItem, headerItem, false),
            arguments(accountItem.copy(ciIcon = 11), accountItem.copy(ciIcon = 10), true),
            arguments(
                accountItem.copy(account = account.copy(name = "1")),
                accountItem.copy(account = account.copy(name = "2")),
                true,
            ),
            arguments(
                accountItem.copy(account = account.copy(localId = AccountId(1))),
                accountItem.copy(account = account.copy(localId = AccountId(2))),
                false,
            ),
            arguments(headerItem.copy(ciName = 11), headerItem.copy(ciName = 10), false),
        )
    }
}
