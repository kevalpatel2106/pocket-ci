package com.kevalpatel2106.connector.bitrise.network.endpoint.responses

internal object GetLogsResponses {
    const val archivedUrl = "https://bitrise-build-log-archives-production.s3.us-east-1.amazonaws.com/build-logs-v2/e9071b5776a1ce8f/fc30dc904d3705c9/30109905/fc30dc904d3705c9.log?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIV2YZWMVCNWNR2HA%2F20220825%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220825T165453Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&x-id=GetObject&X-Amz-Signature=73689f114bc3c14cef21561ecf483e18c9e403d111d970776da489b837433dce"

    val SUCCESS_NOT_ARCHIVED = """
        {
          "log_chunks": [],
          "next_before_timestamp": "0001-01-01T00:00:00Z",
          "next_after_timestamp": "0001-01-01T00:00:00Z",
          "is_archived": false
       }

    """.trimIndent()

    val SUCCESS_ARCHIVED = """
        {
          "log_chunks": [],
          "next_before_timestamp": "0001-01-01T00:00:00Z",
          "next_after_timestamp": "0001-01-01T00:00:00Z",
          "is_archived": true,
          "expiring_raw_log_url": "$archivedUrl"
       }
    """.trimIndent()

    val UNAUTHORISED = """
        {
          "message": "Bad credentials",
        }
    """.trimIndent()
}
