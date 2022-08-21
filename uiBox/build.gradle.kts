plugins {
    id(BuildPlugins.library)
    id(BuildPlugins.kotlinAndroid)
    kotlin(BuildPlugins.kapt)
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

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
    implementation(Libs.Default.coreKtx)
    implementation(Libs.Default.appcompat)
    implementation(Libs.Default.material)
    // Picasso
    implementation(Libs.Default.picasso)
    // Lottie
    implementation(Libs.Default.lottie)

    testImplementation(Libs.Test.junit)
    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.robolectric)
    testImplementation(Libs.Test.testCore)
    testImplementation(Libs.Test.kluent)
    testImplementation(Libs.Test.kluentAndroid)
    testImplementation(Libs.Test.kotlinTestJunit)
}
