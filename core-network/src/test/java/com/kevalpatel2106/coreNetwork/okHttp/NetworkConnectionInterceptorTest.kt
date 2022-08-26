package com.kevalpatel2106.coreNetwork.okHttp

import com.kevalpatel2106.coreNetwork.usecase.IsNetworkConnectedCheck
import com.kevalpatel2106.entity.exception.NoInternetException
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.net.HttpURLConnection.HTTP_OK

internal class NetworkConnectionInterceptorTest {
    private val isNetworkConnectedCheck = mock<IsNetworkConnectedCheck>()
    private val testUrl = "http://example.com/"
    private val testRequest = Request.Builder().url(testUrl).build()
    private val testResponse = Response.Builder()
        .request(testRequest)
        .protocol(Protocol.HTTP_1_1)
        .message("message")
        .code(HTTP_OK)
        .build()
    private val subject = NetworkConnectionInterceptor(isNetworkConnectedCheck)
    private val mockChain = mock<Interceptor.Chain> {
        on { request() } doReturn testRequest
        on { proceed(any()) } doReturn testResponse
    }

    @Test
    fun `given network not available when processing chain then NoInternetException thrown`() {
        whenever(isNetworkConnectedCheck()).thenReturn(false)

        val error = assertThrows<NoInternetException> { subject.intercept(mockChain) }

        assertEquals(testUrl, error.url)
    }

    @Test
    fun `given network available when processing chain then network chain proceeds`() {
        whenever(isNetworkConnectedCheck()).thenReturn(true)

        subject.intercept(mockChain)

        verify(mockChain).proceed(testRequest)
    }
}
