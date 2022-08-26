@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    id("sandboxApplicationScript")
    id("kotlinx-serialization")
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.realmPlugin)
    alias(libs.plugins.ktlinPlugin)
    id("ktlintScript")
    id("sandbox.android.dependencyUpdater")
    id("sandbox.android.sandboxJacoco")
}

android {
    defaultConfig {
        applicationId = "com.example.sandbox"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = project.properties["RELEASE_BASE_URL"].toString()
            )
            manifestPlaceholders["appName"] = "@string/app_name"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = project.properties["DEBUG_BASE_URL"].toString()
            )
            manifestPlaceholders["appName"] = "@string/app_name_debug"
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    kotlinOptions {
        freeCompilerArgs = listOf(
            "-Xallow-jvm-ir-dependencies",
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
    }

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    // uiBox
    implementation(project(":uiBox"))

    // Kotlin
    implementation(libs.kotlinStdlib)
    implementation(libs.coreKtx)
    implementation(libs.lifecycleRuntimeKtx)

    // ui
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.rightBottomSheet)

    // Koin for Android
    implementation(libs.koinAndroid)
    implementation(libs.koinAnnotations)
    ksp(libs.koinKspCompiler)

    // activity
    implementation(libs.activityKtx)
    implementation(libs.fragmentKtx)

    // DataStore
    implementation(libs.datastore)

    // Picasso
    implementation(libs.picasso)

    // Serialization
    implementation(libs.serialization)
    implementation(libs.serializationConverter)
    implementation(libs.datetime)

    // splash screen
    implementation(libs.splashscreen)

    // Pagging
    testImplementation(libs.pagingCommonKtx)
    implementation(libs.pagingRuntimeKtx)

    // Navigation
    implementation(libs.navigationFragmentKtx)
    implementation(libs.navigationUiKtx)

    // realm
    implementation(libs.realm)

    // coroutine
    implementation(libs.coroutinesAndroid)
    implementation(libs.coroutinesCore)

    // okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttpLoggingInterceptor)

    // retrofit
    implementation(libs.retrofit)

    // Facebook shimmer
    implementation(libs.shimmer)

    // Lottie
    implementation(libs.lottie)

    // Leak Canary
    // debugImplementation(libs.Dev.leakCanary)

    // tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)
    testImplementation(libs.testCore)
    testImplementation(libs.koinTest)
    testImplementation(libs.koinTestJunit4)
    testImplementation(libs.kluent)
    testImplementation(libs.kluentAndroid)
    testImplementation(libs.kotlinTestJunit)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.turbine)
    // instrumentation test
    debugImplementation(libs.fragmentTesting) {
        exclude(group = "androidx.test", module = "core")
    }
    androidTestImplementation(libs.espressoContrib)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espressoIntents)
    androidTestImplementation(libs.rules)
    androidTestImplementation(libs.extJunitKtx)
    androidTestImplementation(libs.extTruth)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.uiAutomator)
}
