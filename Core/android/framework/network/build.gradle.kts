plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}
val rootPacketName: String by rootProject.extra

android {
    namespace = "${rootPacketName}.network"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    sourceSets {
        getByName("androidTest") {
            java.srcDirs()  // 清空源码目录
            resources.srcDirs()  // 清空资源目录
        }
        getByName("test") {
            java.srcDirs()
            resources.srcDirs()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {
    api("com.squareup.okio:okio:3.9.0")
    api("com.squareup.okhttp3:okhttp:4.12.0")
    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.retrofit2:converter-scalars:2.4.0")
    api("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-android:2.8.7")
}