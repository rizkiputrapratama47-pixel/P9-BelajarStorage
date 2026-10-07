plugins {
    alias(libs.plugins.android.application)
// 1. Tambahkan plugin KSP (Kotlin Symbol Processing) untuk Room
    id("com.google.devtools.ksp")
     // sesuaikan versi Kotlin Anda
}

android {
    namespace = "com.example.p11_latihanstorage"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.p11_latihanstorage"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

// 2. Dependensi Room Database Dasar
    val roomVersion = "2.8.3"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
// Dependensi Lifecycle & Coroutines untuk eksekusi background
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
}