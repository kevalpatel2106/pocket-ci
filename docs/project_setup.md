# Project Setup

## 3 simple step to contribute into this repo...
1. Fork the project. Clone it in your local machine.
2. Make required changes and commit.
3. Generate pull request. Mention all the required description regarding changes you made. If the PR is based on any bug report already filed, make sure to link that issue in your PR description.

Happy coding.:-)

## Project setup FAQs

### How to run all the unit tests?
- Navigate to the root of the project directory.
- Run all the unit tests.
```shell
./gradlew test
```

### Android Studio warning about distributionSha256Sum:
You might get a warning message in Android Studio that looks like this:
```text
It is not fully supported to define `distributionSha256Sum` in `gradle-wrapper.properties`.
```
This refers to the presence of the `distributionSha256Sum` property into gradle-wrapper.properties, which Update Gradle Wrapper action sets by default to increase security against the risk of the Gradle distribution being tampered with. It is totally safe to disable the warning in Android Studio, just choose the option:
```text
Use "..." as checksum for https://.../gradle-X.Y.Z-bin.zip and sync project
```
