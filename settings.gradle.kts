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
//plugins {
//    id("com.google.dagger.hilt.android") version "2.51.1" apply false
//    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
//}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // ✅ لازم تكون موجودة
        gradlePluginPortal()
        mavenCentral()
    }

}


rootProject.name = "ToDoNote"
include(":app")
