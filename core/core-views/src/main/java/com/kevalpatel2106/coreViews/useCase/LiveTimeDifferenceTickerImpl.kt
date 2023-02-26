package com.kevalpatel2106.coreViews.useCase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import java.util.Date
import javax.inject.Inject

internal class LiveTimeDifferenceTickerImpl @Inject constructor(
    private val calculateTickInterval: CalculateTickInterval,
) : LiveTimeDifferenceTicker {

    override operator fun invoke(dateOfEventStart: Date, tickMorePrecise: Boolean) = flow {
        while (true) {
            emit(Date()) // Emit current date
            delay(calculateTickInterval(dateOfEventStart, tickMorePrecise, Date()))
            yield()
        }
    }.catch {
        it.printStackTrace()
    }
}
