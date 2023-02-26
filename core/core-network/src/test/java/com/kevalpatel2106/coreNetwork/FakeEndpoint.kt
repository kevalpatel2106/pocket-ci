package com.kevalpatel2106.coreNetwork

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface FakeEndpoint {

    @GET("string")
    suspend fun stringDownload(): String

    @POST("string")
    suspend fun stringUpload(@Body stringPayload: String)
}
