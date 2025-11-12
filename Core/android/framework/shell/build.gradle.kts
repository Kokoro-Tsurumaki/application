plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "cn.xj.kokoro.mobile.shell"
    compileSdk = 36


    defaultConfig {
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":framework:contract"))


    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    api(libs.androidx.constraintlayout)
    androidTestImplementation(libs.androidx.espresso.core)
}