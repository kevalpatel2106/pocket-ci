package com.kevalpatel2106.connector.github.network.endpoint.responses

/**
 * Documentation: https://docs.github.com/en/rest/actions/workflow-jobs#list-jobs-for-a-workflow-run
 */
internal object GetArtifactsResponses {
    const val artifact1Id = 11L
    const val artifact1Name = "Rails"
    const val artifact1Size = 123456789012345L
    const val artifact2Id = 13L
    const val artifact2Name = "Test output"
    const val artifact2Size = 0L

    val EMPTY = """
        {
        "total_count": 0,
        "artifacts": []
        }
    """.trimIndent()

    val SUCCESS = """
        {
          "total_count": 2,
          "artifacts": [
            {
              "id": $artifact1Id,
              "node_id": "MDg6QXJ0aWZhY3QxMQ==",
              "name": "$artifact1Name",
              "size_in_bytes": $artifact1Size,
              "url": "https://api.github.com/repos/octo-org/octo-docs/actions/artifacts/11",
              "archive_download_url": "https://api.github.com/repos/octo-org/octo-docs/actions/artifacts/11/zip",
              "expired": false,
              "created_at": "2020-01-10T14:59:22Z",
              "expires_at": "2020-03-21T14:59:22Z",
              "updated_at": "2020-02-21T14:59:22Z",
              "workflow_run": {
                "id": 2332938,
                "repository_id": 1296269,
                "head_repository_id": 1296269,
                "head_branch": "main",
                "head_sha": "328faa0536e6fef19753d9d91dc96a9931694ce3"
              }
            },
            {
              "id": $artifact2Id,
              "node_id": "MDg6QXJ0aWZhY3QxMw==",
              "name": "$artifact2Name",
              "size_in_bytes": $artifact2Size,
              "url": "https://api.github.com/repos/octo-org/octo-docs/actions/artifacts/13",
              "archive_download_url": "https://api.github.com/repos/octo-org/octo-docs/actions/artifacts/13/zip",
              "expired": false,
              "created_at": "2020-01-10T14:59:22Z",
              "expires_at": "2020-03-21T14:59:22Z",
              "updated_at": "2020-02-21T14:59:22Z",
              "workflow_run": {
                "id": 2332942,
                "repository_id": 1296269,
                "head_repository_id": 1296269,
                "head_branch": "main",
                "head_sha": "178f4f6090b3fccad4a65b3e83d076a622d59652"
              }
            }
          ]
        }
    """.trimIndent()

    val UNAUTHORISED = """
        {
          "message": "Bad credentials",
          "documentation_url": "https://docs.github.com/rest"
        }
    """.trimIndent()
}
