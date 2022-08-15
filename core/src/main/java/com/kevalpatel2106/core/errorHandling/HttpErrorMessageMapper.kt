package com.kevalpatel2106.core.errorHandling

internal interface HttpErrorMessageMapper {
    operator fun invoke(code: Int): Pair<Int, Int>
}
