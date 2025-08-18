plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.androidx.navigation.safeargs.kotlin)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.lambao.mrbeast_anime"
    compileSdk = 35
    buildToolsVersion = "35.0.0"

    defaultConfig {
        applicationId = "com.lambao.mrbeast_anime"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isShrinkResources = false
            isMinifyEnabled = false
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
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
    dataBinding {
        enable = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("develop") {
            dimension = "version"
            firebaseAppDistribution {
                appId = "1:253049909295:android:cc6486064eb7292e1c16da"
                serviceCredentialsFile = "$rootDir/app/firebase-key.json"
                releaseNotes = getLastCommitMessage()
                testersFile = "$rootDir/app/testers.txt"
            }
        }
    }
}

dependencies {
    implementation(project(":app:base"))
    implementation(project(":data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /* Hilt */
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    /* Retrofit 2 */
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    /* Logging Interceptor */
    implementation(libs.logging.interceptor)

    /* Timber */
    implementation(libs.timber)

    /* Navigation Component */
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)

    /* Glide */
    implementation(libs.glide)
    implementation(libs.glide.transformations)

    /* Youtube player */
    implementation(libs.pierfrancescosoffritti.androidyoutubeplayer)

    /* Paging 3 */
    implementation(libs.androidx.paging.runtime.ktx)

    /* Splash */
    implementation(libs.androidx.core.splashscreen)

    /* Room */
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    /* Flexbox Layout */
    implementation(libs.flexbox)

    /* MPAndroid Chart*/
    implementation(libs.mpandroidchart)
}

/* Hilt: Allow references to generated code*/
kapt {
    correctErrorTypes = true
}

fun getLastCommitMessage(): String {
    return try {
        val process = ProcessBuilder("git", "log", "-1", "--pretty=%B")
            .redirectErrorStream(true)
            .start()

        process.inputStream.bufferedReader().readText().trim()
    } catch (e: Exception) {
        "No commit message found"
    }
}