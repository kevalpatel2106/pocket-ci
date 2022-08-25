## Sample responses

This package contains sample responses for the Bitrise endpoints being used in this project. These responses are used to test DTOs and the json parsing adapters generated.

### How to get sample response?

The easiest way for getting sample responses is to copy/paste it from Bitrise API documentation. 

1. Go to [Bitrise API docs](https://api-docs.bitrise.io).
2. Navigate to the API you want to test. (e.g. [GET /me](https://api-docs.bitrise.io/#/user/user-profile))
3. Copy paste the example response in one of the `Object` as a string.
4. Use that JSON string to enqueue as a response in your mock-webserver. 