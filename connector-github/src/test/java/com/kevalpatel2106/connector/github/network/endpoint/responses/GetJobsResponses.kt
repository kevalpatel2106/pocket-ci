package com.kevalpatel2106.connector.github.network.endpoint.responses

/**
 * Documentation: https://docs.github.com/en/rest/actions/artifacts#list-workflow-run-artifacts
 */
internal object GetJobsResponses {
    const val jobId = 399444496L
    const val conclusion = "success"
    const val status = "completed"
    const val lastStepName = "Complete job"

    val EMPTY = """
        {
        "total_count": 0,
        "jobs": []
        }
    """.trimIndent()

    val SUCCESS = """
      {
          "total_count": 1,
          "jobs": [
            {
              "id": $jobId,
              "run_id": 29679449,
              "run_url": "https://api.github.com/repos/octo-org/octo-repo/actions/runs/29679449",
              "node_id": "MDEyOldvcmtmbG93IEpvYjM5OTQ0NDQ5Ng==",
              "head_sha": "f83a356604ae3c5d03e1b46ef4d1ca77d64a90b0",
              "url": "https://api.github.com/repos/octo-org/octo-repo/actions/jobs/399444496",
              "html_url": "https://github.com/octo-org/octo-repo/runs/399444496",
              "status": "$status",
              "conclusion": "$conclusion",
              "started_at": "2020-01-20T17:42:40Z",
              "completed_at": "2020-01-20T17:44:39Z",
              "name": "build",
              "steps": [
                {
                  "name": "Set up job",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 1,
                  "started_at": "2020-01-20T09:42:40.000-08:00",
                  "completed_at": "2020-01-20T09:42:41.000-08:00"
                },
                {
                  "name": "Run actions/checkout@v2",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 2,
                  "started_at": "2020-01-20T09:42:41.000-08:00",
                  "completed_at": "2020-01-20T09:42:45.000-08:00"
                },
                {
                  "name": "Set up Ruby",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 3,
                  "started_at": "2020-01-20T09:42:45.000-08:00",
                  "completed_at": "2020-01-20T09:42:45.000-08:00"
                },
                {
                  "name": "Run actions/cache@v3",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 4,
                  "started_at": "2020-01-20T09:42:45.000-08:00",
                  "completed_at": "2020-01-20T09:42:48.000-08:00"
                },
                {
                  "name": "Install Bundler",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 5,
                  "started_at": "2020-01-20T09:42:48.000-08:00",
                  "completed_at": "2020-01-20T09:42:52.000-08:00"
                },
                {
                  "name": "Install Gems",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 6,
                  "started_at": "2020-01-20T09:42:52.000-08:00",
                  "completed_at": "2020-01-20T09:42:53.000-08:00"
                },
                {
                  "name": "Run Tests",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 7,
                  "started_at": "2020-01-20T09:42:53.000-08:00",
                  "completed_at": "2020-01-20T09:42:59.000-08:00"
                },
                {
                  "name": "Deploy to Heroku",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 8,
                  "started_at": "2020-01-20T09:42:59.000-08:00",
                  "completed_at": "2020-01-20T09:44:39.000-08:00"
                },
                {
                  "name": "Post actions/cache@v3",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 16,
                  "started_at": "2020-01-20T09:44:39.000-08:00",
                  "completed_at": "2020-01-20T09:44:39.000-08:00"
                },
                {
                  "name": "$lastStepName",
                  "status": "completed",
                  "conclusion": "success",
                  "number": 17,
                  "started_at": "2020-01-20T09:44:39.000-08:00",
                  "completed_at": "2020-01-20T09:44:39.000-08:00"
                }
              ],
              "check_run_url": "https://api.github.com/repos/octo-org/octo-repo/check-runs/399444496",
              "labels": [
                "self-hosted",
                "foo",
                "bar"
              ],
              "runner_id": 1,
              "runner_name": "my runner",
              "runner_group_id": 2,
              "runner_group_name": "my runner group"
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
