plugins {
    id("sandboxLibraryScript")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
}

dependencies {
    implementation(libs.coreKtx)
    implementation(libs.appcompat)
    implementation(libs.material)
    // Picasso
    implementation(libs.picasso)
    // Lottie
    implementation(libs.lottie)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.testCore)
    testImplementation(libs.kluent)
    testImplementation(libs.kluentAndroid)
    testImplementation(libs.kotlinTestJunit)
}
