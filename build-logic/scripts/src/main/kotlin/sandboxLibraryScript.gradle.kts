@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = DepUtils.ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = DepUtils.ConfigData.minSdkVersion
        targetSdk = DepUtils.ConfigData.targetSdkVersion
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = DepUtils.Java.sourceCompatibility
        targetCompatibility = DepUtils.Java.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = DepUtils.Java.jvmTarget
    }
}
