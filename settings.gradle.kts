pluginManagement {
    repositories {
        google()
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
rootProject.name = "GymApp"
include(":app")
include(":core")
include(":domain")
include(":data")
include(":feature_auth")
include(":feature_home")
include(":feature_workout")
include(":feature_onboarding")
