package com.kevalpatel2106.connector.bitrise.network.endpoint.responses

internal object GetBuildsResponses {
    const val workflowId = "e20cbb4bbd961b4a"
    const val commitHash = "acb5820ced9479c074f688cc328bf03f341a511d"
    const val buildNumber = 562L

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
              "triggered_at": "2021-05-03T20:12:59Z",
              "started_on_worker_at": "2021-05-03T20:13:00Z",
              "environment_prepare_finished_at": "2021-05-03T20:13:00Z",
              "finished_at": "2021-05-03T20:28:07Z",
              "slug": "$workflowId",
              "status": 2,
              "status_text": "error",
              "abort_reason": null,
              "is_on_hold": false,
              "is_processed": true,
              "is_status_sent": true,
              "branch": "master",
              "build_number": $buildNumber,
              "commit_hash": "$commitHash",
              "commit_message": "Create FUNDING.yml",
              "tag": null,
              "triggered_workflow": "master-push",
              "triggered_by": "webhook",
              "machine_type_id": "standard",
              "stack_identifier": "linux-docker-android",
              "original_build_params": {
                "commit_hash": "61311ba7d40802ba7fed87b2f16b72089c6f7746",
                "commit_message": "Create FUNDING.yml",
                "branch": "master",
                "base_repository_url": "https://github.com/kevalpatel2106/year-in-progress.git",
                "diff_url": "",
                "commit_paths": [
                  {
                    "added": [
                      ".github/FUNDING.yml"
                    ],
                    "removed": [
                      
                    ],
                    "modified": [
                      
                    ]
                  }
                ]
              },
              "pipeline_workflow_id": null,
              "pull_request_id": 0,
              "pull_request_target_branch": null,
              "pull_request_view_url": null,
              "commit_view_url": "https://github.com/kevalpatel2106/year-in-progress/commit/61311ba7d40802ba7fed87b2f16b72089c6f7746",
              "credit_cost": null
            },
            {
              "triggered_at": "2020-08-24T20:45:16Z",
              "started_on_worker_at": "2020-08-24T20:45:17Z",
              "environment_prepare_finished_at": "2020-08-24T20:45:17Z",
              "finished_at": "2020-08-24T21:11:02Z",
              "slug": "fc30dc904d3705c9",
              "status": 1,
              "status_text": "success",
              "abort_reason": null,
              "is_on_hold": false,
              "is_processed": true,
              "is_status_sent": true,
              "branch": "master",
              "build_number": 304,
              "commit_hash": "6a5fa268f69a71bb55675fd29aea6fde7492a213",
              "commit_message": "Release v1.2.0",
              "tag": null,
              "triggered_workflow": "master-push",
              "triggered_by": "webhook",
              "machine_type_id": "standard",
              "stack_identifier": "linux-docker-android",
              "original_build_params": {
                "commit_hash": "6a5fa268f69a71bb55675fd29aea6fde7492a213",
                "commit_message": "Release v1.2.0",
                "branch": "master",
                "diff_url": "",
                "commit_paths": [
                  {
                    "added": [
                      
                    ],
                    "removed": [
                      
                    ],
                    "modified": [
                      "gradle/config.gradle"
                    ]
                  }
                ]
              },
              "pipeline_workflow_id": null,
              "pull_request_id": 0,
              "pull_request_target_branch": null,
              "pull_request_view_url": null,
              "commit_view_url": "https://github.com/kevalpatel2106/year-in-progress/commit/6a5fa268f69a71bb55675fd29aea6fde7492a213",
              "credit_cost": null
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
        }
    """.trimIndent()
}
