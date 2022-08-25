package com.kevalpatel2106.coreNetwork.converter

import com.kevalpatel2106.coreNetwork.FakeEndpoint
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection.HTTP_OK

internal class StringConverterTest {

    @Test
    fun `given string response for endpoint when calling endpoint then verify string converter handles string response`() =
        runTest {
            val stringResponse = "string response to test"
            server.enqueue(MockResponse().setResponseCode(HTTP_OK).setBody(stringResponse))

            val actual = getFakeEndpoint().stringDownload()

            assertEquals(stringResponse, actual)
        }

    @Test
    fun `given string payload for endpoint when calling endpoint then verify string converter handles string request`() =
        runTest {
            val stringPayload = "string response to test"
            server.enqueue(MockResponse().setResponseCode(HTTP_OK))

            assertDoesNotThrow { getFakeEndpoint().stringUpload(stringPayload) }
        }

    private fun getFakeEndpoint() = Retrofit.Builder()
        .baseUrl(server.url("/").toString())
        .client(OkHttpClient())
        .addConverterFactory(StringConverter.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(FakeEndpoint::class.java)

    companion object {
        private val server = MockWebServer()

        @Before
        fun before() = server.start()

        @After
        fun after() = server.shutdown()
    }
}
