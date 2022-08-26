package com.kevalpatel2106.feature.account.list.adapter

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.feature.account.R
import com.kevalpatel2106.feature.account.getAccountItemFixture
import com.kevalpatel2106.feature.account.list.adapter.AccountsListItem.HeaderItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

internal class AccountListItemTest {

    @ParameterizedTest(name = "given account list item {0} when item created them check compare id is {1}")
    @MethodSource("provideValuesForCompareIdTest")
    fun `given account list item when item created them check compare id`(
        inputAccountsListItem: AccountsListItem,
        expectedCompareId: String,
    ) {
        assertEquals(expectedCompareId, inputAccountsListItem.compareId)
    }

    @ParameterizedTest(name = "given account list item {0} when item created them check list item type is {1}")
    @MethodSource("provideValuesForListItemTypeTest")
    fun `given account list item when item created them check list item type`(
        inputAccountsListItem: AccountsListItem,
        expectedListItemType: AccountsListItemType,
    ) {
        assertEquals(expectedListItemType, inputAccountsListItem.listItemType)
    }

    companion object {
        private val kFixture = KFixture()
        private val accountItem = getAccountItemFixture(kFixture)
        private val headerItem = HeaderItem(R.string.list_header_favourites)

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForCompareIdTest() = listOf(
            // Format: account list item, expected compare id
            arguments(accountItem, accountItem.account.localId.toString()),
            arguments(headerItem, headerItem.ciName.toString()),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForListItemTypeTest() = listOf(
            // Format: account list item, expected list item type
            arguments(accountItem, AccountsListItemType.ACCOUNT_ITEM),
            arguments(headerItem, AccountsListItemType.HEADER_ITEM),
        )
    }
}
