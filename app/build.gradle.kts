plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "ru.tanexc.photosolver"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.tanexc.photosolver"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha13")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Compose
    implementation("androidx.compose.ui:ui:1.6.0-alpha08")
    implementation("androidx.compose.material3:material3:1.2.0-alpha10")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha10")
    implementation("androidx.compose.material:material-icons-core:1.6.0-alpha08")
    implementation("androidx.compose.material:material-icons-extended:1.6.0-alpha08")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha08")
    implementation("androidx.compose.ui:ui-util:1.6.0-alpha08")
    implementation("androidx.compose.material:material:1.5.4")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    ksp("com.google.dagger:hilt-android-compiler:2.48")

    // Coil
    implementation("io.coil-kt:coil:2.5.0")
    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
}