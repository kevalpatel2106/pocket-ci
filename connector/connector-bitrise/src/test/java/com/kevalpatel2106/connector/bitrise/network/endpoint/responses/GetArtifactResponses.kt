package com.kevalpatel2106.connector.bitrise.network.endpoint.responses

internal object GetArtifactResponses {
    const val artifactId = "4d8e37dd5c638198"
    const val artifactName = "app-release-bitrise-signed.apk"
    const val artifactSize = 123456789012345L

    val SUCCESS_ARCHIVED = """
        {
          "data": {
            "title": "$artifactName",
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
            "expiring_download_url": "https://bitrise-prod-build-storage.s3.amazonaws.com/builds/fc30dc904d3705c9/artifacts/47459715/app-release-bitrise-signed.apk?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIV2YZWMVCNWNR2HA%2F20220825%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220825T165157Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=a17506039eb94663381037fe78725351696dae666defb1e63eff58b83708dd4e",
            "is_public_page_enabled": true,
            "slug": "$artifactId",
            "public_install_page_url": "https://www.bitrise.io/artifact/47459715/p/02d2e01920f0b818046507a764467d0d",
            "file_size_bytes": $artifactSize
          }
        }
    """.trimIndent()

    val UNAUTHORISED = """
        {
          "message": "Bad credentials",
        }
    """.trimIndent()
}
