## Sample responses

This package contains sample responses for the GitHub endpoints being used in this project. These
responses are used to test DTOs and the json parsing adapters generated.

### How to get sample response?

The easiest way for getting sample responses is to copy/paste it from GitHub API documentation.

1. Go to [GitHub API docs](https://docs.github.com/en/rest).
2. Navigate to the API you want to test. (
   e.g. [GET /user](https://docs.github.com/en/rest/users/users#get-the-authenticated-user))
3. Copy paste the example response in one of the `Object` as a string.
4. Use that JSON string to enqueue as a response in your mock-webserver.
