package com.kevalpatel2106.connector.bitrise.network.endpoint.responses

internal object GetRepositoriesResponses {
    const val project = "Hello-World"
    const val projectOwner = "octocat"

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
              "slug": "e9071b5776a1ce8f",
              "title": "$project",
              "project_type": "android",
              "provider": "github",
              "repo_owner": "$projectOwner",
              "repo_url": "git@github.com:kevalpatel2106/year-in-progress.git",
              "repo_slug": "year-in-progress",
              "is_disabled": false,
              "status": 1,
              "is_public": false,
              "is_github_checks_enabled": false,
              "owner": {
                "account_type": "user",
                "name": "kevalpatel2106",
                "slug": "951ca201bf8acf9e"
              },
              "avatar_url": "https://concrete-userfiles-production.s3.us-west-2.amazonaws.com/repositories/e9071b5776a1ce8f/avatar/avatar.png"
            },
            {
              "slug": "70be2acc146e73a1",
              "title": "Fx-Rates-App",
              "project_type": "android",
              "provider": "github",
              "repo_owner": "kevalpatel2106",
              "repo_url": "https://github.com/kevalpatel2106/Fx-Rates-App.git",
              "repo_slug": "Fx-Rates-App",
              "is_disabled": false,
              "status": 1,
              "is_public": true,
              "is_github_checks_enabled": true,
              "owner": {
                "account_type": "user",
                "name": "kevalpatel2106",
                "slug": "951ca201bf8acf9e"
              },
              "avatar_url": "https://concrete-userfiles-production.s3.us-west-2.amazonaws.com/repositories/70be2acc146e73a1/avatar/avatar.png"
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
