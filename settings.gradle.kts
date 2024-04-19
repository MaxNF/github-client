pluginManagement {
    includeBuild("build-logic")
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

rootProject.name = "Github client"
include(":app")
include(":designsystem")
include(":common")
include(":common-test")

include(":feature:user-list:data")
include(":feature:user-list:domain")
include(":feature:user-list:ui")

include(":feature:user-details:data")
include(":feature:user-details:domain")
include(":feature:user-details:ui")
