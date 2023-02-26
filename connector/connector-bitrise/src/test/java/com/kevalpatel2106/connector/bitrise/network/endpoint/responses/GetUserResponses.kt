package com.kevalpatel2106.connector.bitrise.network.endpoint.responses

internal object GetUserResponses {
    const val username = "kevalpatel2106"
    const val slug = "jhfasdfds87asu"
    const val email = "octocat@bitrise.com"
    val SUCCESS = """
        {
          "data": {
            "username": "$username",
            "slug": "$slug",
            "email": "$email",
            "avatar_url": "https://concrete-userfiles-production.s3.us-west-2.amazonaws.com/users/jhfasdfds87asu/avatar/avatar.jpg",
            "created_at": "2017-04-13T06:58:33.803893Z",
            "has_used_organization_trial": true,
            "data_id": 18512,
            "payment_processor": "Stripe"
          }
        }
    """.trimIndent()

    val UNAUTHORISED = """
        {
          "message": "Bad credentials",
        }
    """.trimIndent()
}
