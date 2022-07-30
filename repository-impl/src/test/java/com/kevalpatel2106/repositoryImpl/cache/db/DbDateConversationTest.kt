package com.kevalpatel2106.repositoryImpl.cache.db

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

class DbDateConversationTest {

    @Test
    fun `given date when date converted to long check long value is unix mills`() {
        // given
        val inputDate = Date(System.currentTimeMillis())

        // when
        val convertedType = DbTypeConverter.fromDate(inputDate)

        // check
        assertEquals(inputDate.time, convertedType)
    }

    @Test
    fun `given long value when millisecond converted to date check date`() {
        // given
        val currentMills = System.currentTimeMillis()

        // when
        val convertedType = DbTypeConverter.toDate(currentMills)

        // check
        assertEquals(currentMills, convertedType.time)
    }
}
