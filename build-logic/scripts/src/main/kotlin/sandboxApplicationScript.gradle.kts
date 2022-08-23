@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file(DepUtils.KeyStore.keyStorePath)
            storePassword = DepUtils.KeyStore.keyStorePassword
            keyAlias = DepUtils.KeyStore.keyStoreAlias
            keyPassword = DepUtils.KeyStore.keyStoreKeyPassword
        }
        getByName("debug") {
            storeFile = file(DepUtils.KeyStore.keyStorePath)
            storePassword = DepUtils.KeyStore.keyStorePassword
            keyAlias = DepUtils.KeyStore.keyStoreAlias
            keyPassword = DepUtils.KeyStore.keyStoreKeyPassword
        }
    }

    compileSdk = DepUtils.ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = DepUtils.ConfigData.minSdkVersion
        targetSdk = DepUtils.ConfigData.targetSdkVersion
        versionCode = DepUtils.ConfigData.versionCode
        versionName = DepUtils.ConfigData.versionName
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = DepUtils.Java.sourceCompatibility
        targetCompatibility = DepUtils.Java.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = DepUtils.Java.jvmTarget
    }

    bundle {
        language {
            enableSplit = true
        }
        density {
            enableSplit = true
        }
        abi {
            enableSplit = true
        }
    }
}
