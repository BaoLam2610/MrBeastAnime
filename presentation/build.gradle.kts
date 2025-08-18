plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.lambao.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    // Core module dependency
    implementation(project(":core"))
    
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    
    // AndroidX AppCompat
    implementation(libs.androidx.appcompat)
    
    // Material Design
    implementation(libs.material)
    
    // Activity
    implementation(libs.androidx.activity)
    
    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    
    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)

    // Glide
    implementation(libs.glide)
    implementation(libs.glide.transformations)

    // Paging 3
    implementation(libs.androidx.paging.runtime.ktx)
}
