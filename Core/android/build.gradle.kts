import org.gradle.kotlin.dsl.getByName

// Top-level build file where you can add configuration options common to all sub-projects/modules.
val rootPacketName by extra("kokoro.mobile")
apply(from = "gradle/tasks/test-tasks.gradle.kts")
apply(from = "gradle/tasks/template-mvi-tasks.gradle.kts")
apply(from = "gradle/tasks/file-tasks.gradle.kts")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false


    id("com.google.dagger.hilt.android") version "2.56.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    id("androidx.room") version "2.8.0" apply false
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
