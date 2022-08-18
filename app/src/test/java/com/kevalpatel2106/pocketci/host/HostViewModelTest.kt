package com.kevalpatel2106.pocketci.host

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.TestCoroutineExtension
import com.kevalpatel2106.coreTest.runTestObservingSharedFlow
import com.kevalpatel2106.pocketci.R
import com.kevalpatel2106.pocketci.host.AppNavigationConfig.SCREENS_WITH_BOTTOM_DRAWER
import com.kevalpatel2106.pocketci.host.AppNavigationConfig.SCREENS_WITH_NO_TOOLBAR
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

@ExtendWith(TestCoroutineExtension::class)
internal class HostViewModelTest {
    private val subject by lazy { HostViewModel() }

    @ParameterizedTest(name = "given previous destination id {0} and next destination id {1} when on destination changed called check navigation icon is {2}")
    @MethodSource("provideValuesForNavIconOnNavDestinationChanged")
    fun `given previous and next destination id when on destination changed called check navigation icon`(
        previousDstId: Int?,
        nextDstId: Int,
        expected: Int?,
    ) = runTest {
        subject.onNavDestinationChanged(previousDstId, nextDstId)
        advanceUntilIdle()

        assertEquals(expected, subject.viewState.value.navigationIcon)
    }

    @ParameterizedTest(name = "given previous destination id {0} and next destination id {1} when on destination changed called check navigation visibility is {2}")
    @MethodSource("provideValuesForVisibilityOfNavIconOnNavDestinationChanged")
    fun `given previous and next destination id when on destination changed called check navigation visibility`(
        previousDstId: Int?,
        nextDstId: Int,
        expected: Boolean,
    ) = runTest {
        subject.onNavDestinationChanged(previousDstId, nextDstId)
        advanceUntilIdle()

        assertEquals(expected, subject.viewState.value.navigationIconVisible)
    }

    @ParameterizedTest(name = "given current destination id {0} when on navigate up called check event is {1}")
    @MethodSource("provideValuesForNavigateUp")
    fun `given current destination id when on navigate up called check event`(
        currentDstId: Int?,
        expectedEvent: HostVMEvents,
    ) = runTestObservingSharedFlow(subject.vmEventsFlow) { _, flowTurbine ->
        subject.onNavigateUpClicked(currentDstId)

        assertEquals(expectedEvent, flowTurbine.awaitItem())
    }

    companion object {
        private val fixture = KFixture()
        private val navDestinationIdFixture: Int
            get() = fixture.range(0..100)

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForNavigateUp() = listOf(
            // Format current dst, expected vm event
            arguments(null, HostVMEvents.NavigateUp),
            arguments(navDestinationIdFixture, HostVMEvents.NavigateUp),
        ).toMutableList().apply {
            SCREENS_WITH_BOTTOM_DRAWER.forEach {
                add(arguments(it, HostVMEvents.ShowBottomDialog))
            }
        }

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForNavIconOnNavDestinationChanged() = listOf(
            // Format previous dst, next dst, nav icon
            arguments(null, navDestinationIdFixture, null),
            arguments(R.id.splash, navDestinationIdFixture, R.drawable.ic_arrow_back),
        ).toMutableList().apply {
            SCREENS_WITH_BOTTOM_DRAWER.forEach {
                add(arguments(R.id.splash, it, R.drawable.ic_hamburger))
                add(arguments(null, it, R.drawable.ic_hamburger))
            }
        }

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForVisibilityOfNavIconOnNavDestinationChanged() = listOf(
            // Format previous dst, next dst, nav icon is visible
            arguments(null, navDestinationIdFixture, true),
            arguments(R.id.splash, navDestinationIdFixture, true),
        ).toMutableList().apply {
            SCREENS_WITH_NO_TOOLBAR.forEach {
                add(arguments(null, it, false))
                add(arguments(R.id.splash, it, false))
            }
        }
    }
}
