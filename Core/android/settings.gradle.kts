pluginManagement {
    repositories {
        mavenLocal()
//        // 阿里云 Maven 中央库
//        maven ("https://maven.aliyun.com/repository/central")
//        // 阿里云 Google Maven 库
//        maven ("https://maven.aliyun.com/repository/google")
//        // 阿里云 Gradle Plugin 库
//        maven ("https://maven.aliyun.com/repository/gradle-plugin")
//        // 阿里云 JitPack 库
//        maven ("https://maven.aliyun.com/repository/jitpack")
//
//        maven("https://maven.aliyun.com/nexus/content/repositories/releases")
//        maven("https://maven.aliyun.com/nexus/content/groups/public/")
//        maven("https://maven.aliyun.com/nexus/content/repositories/jcenter")
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
//        // 阿里云 Maven 中央库
//        maven ("https://maven.aliyun.com/repository/central")
//        // 阿里云 Google Maven 库
//        maven ("https://maven.aliyun.com/repository/google")
//        // 阿里云 Gradle Plugin 库
//        maven ("https://maven.aliyun.com/repository/gradle-plugin")
//        // 阿里云 JitPack 库
//        maven ("https://maven.aliyun.com/repository/jitpack")
//
//        maven("https://maven.aliyun.com/nexus/content/repositories/releases")
//        maven("https://maven.aliyun.com/nexus/content/groups/public/")
//        maven("https://maven.aliyun.com/nexus/content/repositories/jcenter")
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}




rootProject.name = "android"
include(":application:demo")
include(":application:bad-cat")
//include(":application:mvi-template")
include(":framework:shell")
include(":framework:album")
include(":framework:network")
include(":framework:contract")
include(":lib:opencv-sdk")
include(":lib:opencv-mobile")

