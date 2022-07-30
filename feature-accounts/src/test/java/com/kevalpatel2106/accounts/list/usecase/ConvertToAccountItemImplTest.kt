package com.kevalpatel2106.accounts.list.usecase

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.accounts.R
import com.kevalpatel2106.coreTest.getAccountFixture
import com.kevalpatel2106.coreTest.getCIInfoFixture
import com.kevalpatel2106.repository.CIInfoRepo
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ConvertToAccountItemImplTest {
    private val kFixture = KFixture()
    private val testCIInfo = getCIInfoFixture(kFixture())
    private val mockCiRepo = mock<CIInfoRepo>()
    private val subject = ConvertToAccountItemImpl(mockCiRepo)

    @Test
    fun `given account when invoke then check account in AccountItem`() = runTest {
        val account = getAccountFixture(kFixture)
        whenever(mockCiRepo.getCI(account.type)).thenReturn(testCIInfo)

        val result = subject.invoke(account)

        assertEquals(account, result.account)
    }

    @Test
    fun `given account when invoke then check ci icon in AccountItem`() = runTest {
        val account = getAccountFixture(kFixture)
        whenever(mockCiRepo.getCI(account.type)).thenReturn(testCIInfo)

        val result = subject.invoke(account)

        assertEquals(testCIInfo.icon, result.ciIcon)
    }

    @Test
    fun `given account when invoke then check ci name in AccountItem`() = runTest {
        val account = getAccountFixture(kFixture)
        whenever(mockCiRepo.getCI(account.type)).thenReturn(testCIInfo)

        val result = subject.invoke(account)

        assertEquals(testCIInfo.name, result.ciName)
    }

    @Test
    fun `given account which is selected when invoke then check stroke width is 2dp`() = runTest {
        val account = getAccountFixture(kFixture).copy(isSelected = true)
        whenever(mockCiRepo.getCI(account.type)).thenReturn(testCIInfo)

        val result = subject.invoke(account)

        assertEquals(R.dimen.selected_account_stroke_width, result.imageStrokeWidth)
    }

    @Test
    fun `given account which is not selected when invoke then check stroke width is 0dp`() =
        runTest {
            val account = getAccountFixture(kFixture).copy(isSelected = false)
            whenever(mockCiRepo.getCI(account.type)).thenReturn(testCIInfo)

            val result = subject.invoke(account)

            assertEquals(R.dimen.unselected_account_stroke_width, result.imageStrokeWidth)
        }
}
