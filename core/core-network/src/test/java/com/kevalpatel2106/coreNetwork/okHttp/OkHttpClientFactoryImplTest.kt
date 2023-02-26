package com.kevalpatel2106.coreNetwork.okHttp

import com.kevalpatel2106.coreNetwork.usecase.IsNetworkConnectedCheck
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import java.util.concurrent.TimeUnit

internal class OkHttpClientFactoryImplTest {
    private val isNetworkConnectedCheck = mock<IsNetworkConnectedCheck>()
    private val flavouredInterceptor = mock<FlavouredInterceptor>()
    private val subject = OkHttpClientFactoryImpl(isNetworkConnectedCheck, flavouredInterceptor)

    @Test
    fun `verify read timeout is 1 minute`() {
        val actual = subject.getOkHttpClient().readTimeoutMillis.toLong()

        assertEquals(TimeUnit.MINUTES.toMillis(1), actual)
    }

    @Test
    fun `verify write timeout is 1 minute`() {
        val actual = subject.getOkHttpClient().writeTimeoutMillis.toLong()

        assertEquals(TimeUnit.MINUTES.toMillis(1), actual)
    }

    @Test
    fun `verify connection timeout is 1 minute`() {
        val actual = subject.getOkHttpClient().connectTimeoutMillis.toLong()

        assertEquals(TimeUnit.MINUTES.toMillis(1), actual)
    }

    @Test
    fun `verify network connection interceptor is added`() {
        val actual = subject.getOkHttpClient().interceptors

        assertTrue(actual.first() is NetworkConnectionInterceptor)
    }
}
