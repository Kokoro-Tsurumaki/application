plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("androidx.room")
}

val rootPacketName: String by rootProject.extra
android {
    namespace = "${rootPacketName}.hachimi"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        applicationId = "${rootPacketName}.hachimi"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        register("release") {
            enableV1Signing = true
            enableV2Signing = true
            storeFile =  File("D:\\Develop\\Android\\AndroidProject\\xiaola\\hostalonlinesandroid\\jks\\hos.jks")
            storePassword = "supply"
            keyAlias = "supply"
            keyPassword ="supply"
        }
    }

    buildTypes {
        release {
            // Enables code-related app optimization.
            isMinifyEnabled = true
            // Enables resource shrinking.
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17


    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

}

dependencies {
    implementation ("io.github.ehsannarmani:compose-charts:0.1.11")

    implementation(project(":framework:shell"))
    implementation(project(":framework:contract"))
    implementation(project(":framework:network"))
    implementation("androidx.room:room-runtime:2.8.0")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:2.8.0")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.8.0")

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.9.3")
    implementation("androidx.lifecycle:lifecycle-runtime-compose-android:2.9.3")
    implementation("androidx.navigation:navigation-compose:2.9.4")


    api("com.google.dagger:hilt-android:2.56.2")
    implementation(libs.androidx.appcompat)
    ksp("com.google.dagger:hilt-android-compiler:2.56.2")
    api("androidx.hilt:hilt-navigation-compose:1.3.0")

    implementation("io.coil-kt:coil:2.5.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")
    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.datastore:datastore:1.1.7")
    implementation("androidx.datastore:datastore-preferences:1.1.7")


//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}