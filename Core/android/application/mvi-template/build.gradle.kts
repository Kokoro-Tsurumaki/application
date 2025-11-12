plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}
val rootPacketName: String by rootProject.extra
android {
    namespace = "${rootPacketName}.template"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        applicationId = "${rootPacketName}.template"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
}

dependencies {
    implementation(project(":framework:shell"))
    implementation(project(":framework:network"))
    implementation(project(":framework:contract"))
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)



//    implementation("androidx.activity:activity-compose:1.8.0")
//    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.ui:ui-tooling")
//    implementation("androidx.compose.material3:material3")
//    implementation(libs.androidx.lifecycle.runtime.ktx)

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

//    androidTestImplementation(platform("androidx.compose:compose-bom:2025.09.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    androidTestImplementation("androidx.compose.ui:ui-test-manifest")

    androidTestImplementation(libs.androidx.espresso.core)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}