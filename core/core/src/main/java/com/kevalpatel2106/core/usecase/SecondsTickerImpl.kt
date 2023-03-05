package com.kevalpatel2106.core.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.yield
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class SecondsTickerImpl @Inject constructor() : SecondsTicker {

    override operator fun invoke() = flow {
        while (true) {
            emit(System.currentTimeMillis()) // Emit current date
            delay(ONE_SECOND_MILLS)
            yield()
        }
    }.catch {
        it.printStackTrace()
    }

    companion object {
        private val ONE_SECOND_MILLS = TimeUnit.SECONDS.toMillis(1)
    }
}
