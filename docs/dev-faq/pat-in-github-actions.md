## Why do we use GitHub personal action token (PAT) in GitHub Actions?

## Problem with opening and merging PRs using GitHub Actions:
By default, if the `token` input in any step which triggers workflow (e.g. opening PR or merging PR) is left empty or if you set it to `GITHUB_TOKEN`, Pull Requests created by the Update Gradle Wrapper action do not trigger any other workflow. So, for example, if you have any `on: pull_request` or `on: push` workflow that runs CI checks on Pull Requests, they won't normally be triggered.

This is a restriction imposed by GitHub Actions to avoid accidentally creating recursive workflow runs ([read more](https://docs.github.com/en/actions/reference/events-that-trigger-workflows#triggering-new-workflows-using-a-personal-access-token)).

### Here is what you can do to trigger additional

Use a [Personal Access Token](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token): create a PAT with the `repo` scope and add it [as a secret](https://docs.github.com/en/actions/reference/encrypted-secrets#creating-encrypted-secrets-for-a-repository) into your repository. Then configure the `token` input to use such encrypted secret.
> Note that the Pull Request author will be set to the GitHub user that the PAT belongs to: as Pull Request author, this user cannot be assigned as reviewer and cannot approve it.
**PR:** https://github.com/kevalpatel2106/pocket-ci/pull/16
