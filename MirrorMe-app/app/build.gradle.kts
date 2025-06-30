plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.example.mirrorme"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mirrorme"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation ("androidx.compose.material:material-icons-extended")
    implementation ("androidx.navigation:navigation-compose:2.9.0" )
    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.0-beta-1"))
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.ktor:ktor-client-okhttp:3.1.1")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:1.3.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.3.1")
    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.5.3")
    implementation("io.github.jan-tennert.supabase:supabase-kt:1.5.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Navigation library
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")

    // CameraX core library
    implementation ("androidx.camera:camera-core:1.2.0-alpha02")

    // CameraX Camera2 extensions
    implementation ("androidx.camera:camera-camera2:1.2.0-alpha02")

    // CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:1.2.0-alpha02")

    // CameraX View class
    implementation ("androidx.camera:camera-view:1.2.0-alpha02")

    // WindowManager
    implementation ("androidx.window:window:1.1.0-alpha03")


    // MediaPipe Library
    implementation ("com.google.mediapipe:tasks-vision:0.10.14")

    implementation("io.github.sceneview:sceneview:2.3.0")

    // models dependencies
    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter (to parse JSON into your data models)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // (Optional but recommended for logging network calls)
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.1")

}