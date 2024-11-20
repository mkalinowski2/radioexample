pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RadioExample"
include(
    ":app",
    ":android-ui",
    ":android-app-navigator",
    ":component-radioplayer",
    ":core-home",
    ":feature-home",
    ":feature-radiodetails",
    ":lib-network"
)

project(":android-ui").projectDir = file("android-modules/android-ui")
project(":android-app-navigator").projectDir = file("android-modules/android-app-navigator")
project(":component-radioplayer").projectDir = file("component-modules/component-radioplayer")
project(":core-home").projectDir = file("core-modules/core-home")
project(":feature-home").projectDir = file("feature-modules/feature-home")
project(":feature-radiodetails").projectDir = file("feature-modules/feature-radiodetails")
project(":lib-network").projectDir = file("lib-modules/lib-network")
