plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.gym.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {
    api(libs.kotlin.stdlib)
    api(libs.compose.ui)
    api(libs.compose.material3)

    api(libs.lifecycle.viewmodel.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.coroutines.core)
    api(libs.coroutines.android)
    api(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ML Kit Translation & Play Services Tasks (for compile)
    api("com.google.mlkit:translate:17.0.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
}
