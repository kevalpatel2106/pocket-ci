pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    // Version catalog is not supported here.
    id("com.gradle.enterprise") version "3.12.5"
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        exclusiveContent {
            forRepository {
                google()
            }
            filter {
                includeGroupByRegex("androidx.*")
            }
        }
    }
}
gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        if (System.getenv("CI").toBoolean()) {
            publishAlways()
            tag("CI")
        }
    }
}

rootProject.name = "Pocket CI"
rootDir.walk().maxDepth(2).filter {
    val notBuildSrc = it.name != "buildSrc"
    val notTemplateDir = !it.name.contains("template")
    val containsBuildFile = File(it, "build.gradle").exists() || File(it, "build.gradle.kts").exists()
    notBuildSrc && notTemplateDir && containsBuildFile
}.forEach {
    if (it.parentFile == rootDir) {
        include(it.name)
    } else {
        include("${it.parentFile.name}:${it.name}")
    }
}