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
        applicationId = DepUtils.ConfigData.applicationId
    }

    namespace = DepUtils.ConfigData.applicationId

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        managedDevices {
            devices {
                maybeCreate<com.android.build.api.dsl.ManagedVirtualDevice>("pixel2api30").apply {
                    // Use device profiles you typically see in Android Studio.
                    device = "Pixel 2"
                    // Use only API levels 27 and higher.
                    apiLevel = 30
                    // To include Google services, use "google".
                    systemImageSource = "aosp-atd"
                }
            }
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

    lint {
        sarifReport = true
        checkDependencies = true // to merge reports from sub modules
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
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
