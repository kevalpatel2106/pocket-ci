package com.kevalpatel2106.feature.drawer

import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.feature.drawer.drawer.BottomDrawerViewModel
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerItem
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent.OpenAccountsAndClose
import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent.OpenSettingsAndClose
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class BottomDrawerViewModelTest {
    private val subject = BottomDrawerViewModel()

    @Test
    fun `when settings clicked then settings opened and drawer closed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onDrawerItemClicked(BottomDrawerItem.Settings)

            assertEquals(OpenSettingsAndClose, flowTurbine.awaitItem())
        }

    @Test
    fun `when accounts clicked then accounts list opened and drawer closed`() =
        runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
            subject.onDrawerItemClicked(BottomDrawerItem.LinkedAccounts)

            assertEquals(OpenAccountsAndClose, flowTurbine.awaitItem())
        }
}
