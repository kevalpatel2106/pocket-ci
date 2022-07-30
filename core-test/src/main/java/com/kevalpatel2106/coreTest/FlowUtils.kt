package com.kevalpatel2106.coreTest

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

fun <T> StateFlow<T>.latestValue(coroutineScope: TestScope): T {
    coroutineScope.advanceUntilIdle()
    return value
}

fun <T> runTestObservingSharedFlow(
    sharedFlow: SharedFlow<T>,
    testBody: suspend (testScope: TestScope, flowTurbine: FlowTurbine<T>) -> Unit,
) = runTest {
    sharedFlow.test {
        testBody(this@runTest, this@test)
    }
}
