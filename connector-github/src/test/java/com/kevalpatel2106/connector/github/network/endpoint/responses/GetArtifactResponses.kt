package com.kevalpatel2106.connector.github.network.endpoint.responses

/**
 * Documentation: https://docs.github.com/en/rest/actions/artifacts#get-an-artifact
 */
internal object GetArtifactResponses {
    const val artifactId = 11L
    const val artifactName = "Rails"
    const val artifactSize = 123456789012345L

    val SUCCESS = """
        {
          "id": $artifactId,
          "node_id": "MDg6QXJ0aWZhY3QxMQ==",
          "name": "$artifactName",
          "size_in_bytes": $artifactSize,
          "url": "https://api.github.com/repos/octo-org/octo-docs/actions/artifacts/11",
          "archive_download_url": "https://api.github.com/repos/octo-org/octo-docs/actions/artifacts/11/zip",
          "expired": false,
          "created_at": "2020-01-10T14:59:22Z",
          "expires_at": "2020-01-21T14:59:22Z",
          "updated_at": "2020-01-21T14:59:22Z",
          "workflow_run": {
            "id": 2332938,
            "repository_id": 1296269,
            "head_repository_id": 1296269,
            "head_branch": "main",
            "head_sha": "328faa0536e6fef19753d9d91dc96a9931694ce3"
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
