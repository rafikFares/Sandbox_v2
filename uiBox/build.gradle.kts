plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    val rootExtra = rootProject.extra

    compileSdk = rootExtra["compileSdkVersion"] as Int

    defaultConfig {
        minSdk = rootExtra["minSdkVersion"] as Int
        targetSdk = rootExtra["targetSdkVersion"] as Int

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    val rootExtra = rootProject.extra

    fun getDependency(key: String): String = rootExtra[key] as String

    implementation(getDependency("coreKtx"))
    implementation(getDependency("appcompat"))
    implementation(getDependency("material"))
    // Picasso
    implementation(getDependency("picasso"))
    // Lottie
    implementation(getDependency("lottie"))

    testImplementation(getDependency("junit"))
    testImplementation(getDependency("mockk"))
    testImplementation(getDependency("robolectric"))
    testImplementation(getDependency("testCore"))
    testImplementation(getDependency("kluent"))
    testImplementation(getDependency("kluentAndroid"))
    testImplementation(getDependency("kotlinTestJunit"))
}
