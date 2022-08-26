package com.kevalpatel2106.connector.bitrise.network.endpoint.responses

internal object GetArtifactsResponses {
    const val artifact1Id = "4d8e37dd5c638198"
    const val artifact1Name = "app-release-bitrise-signed.apk"
    const val artifact1Size = 123456789012345L
    const val artifact2Id = "f08798cf022ee6d1"
    const val artifact2Name = "app-mapping.txt"
    const val artifact2Size = 0L

    val EMPTY = """
        {
          "data": [],
          "paging": {
            "total_item_count": 0,
            "page_item_limit": 40
          }
        }
    """.trimIndent()

    val SUCCESS = """
        {
          "data": [
            {
              "title": "$artifact1Name",
              "artifact_type": "android-apk",
              "artifact_meta": {
                "info_type_id": "android-apk",
                "file_size_bytes": "3558274.000000",
                "module": "app",
                "product_flavour": "",
                "build_type": "release",
                "include": null,
                "universal": "",
                "aab": "",
                "apk": "/bitrise/deploy/app-release-bitrise-signed.apk",
                "split": null,
                "app_info": {
                  "app_name": "Year in Progress",
                  "package_name": "com.kevalpatel2106.yip",
                  "version_code": "23",
                  "version_name": "1.2.0",
                  "min_sdk_version": "21"
                }
              },
              "is_public_page_enabled": true,
              "slug": "$artifact1Id",
              "file_size_bytes": $artifact1Size
            },
            {
              "title": "$artifact2Name",
              "artifact_type": "file",
              "artifact_meta": null,
              "is_public_page_enabled": false,
              "slug": "$artifact2Id",
              "file_size_bytes": $artifact2Size
            }
          ],
          "paging": {
            "total_item_count": 2,
            "page_item_limit": 40
          }
        }
    """.trimIndent()

    val UNAUTHORISED = """
        {
          "message": "Bad credentials",
          "documentation_url": "https://docs.github.com/rest"
        }
    """.trimIndent()
}
