plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("io.realm.kotlin")
    id("kotlinx-serialization")
    kotlin("kapt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    val rootExtra = rootProject.extra

    signingConfigs {
        create("release") {
            storeFile = file("key/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        getByName("debug") {
            storeFile = file("key/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    compileSdk = rootExtra["compileSdkVersion"] as Int

    defaultConfig {
        applicationId = "com.example.sandbox"
        minSdk = rootExtra["minSdkVersion"] as Int
        targetSdk = rootExtra["targetSdkVersion"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String

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
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = project.properties["DEBUG_BASE_URL"].toString()
            )
            manifestPlaceholders["appName"] = "@string/app_name_debug"
            applicationIdSuffix = ".debug"
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
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
        freeCompilerArgs = listOf(
            "-Xallow-jvm-ir-dependencies",
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    applicationVariants.all {
        val variantName = name
        sourceSets {
            getByName("main") {
                java.srcDir(File("build/generated/ksp/$variantName/kotlin"))
            }
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
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

dependencies {

    // uiBox
    implementation(project(":uiBox"))

    val rootExtra = rootProject.extra

    fun getDependency(key: String): String = rootExtra[key] as String

    // Kotlin
    implementation(getDependency("kotlinStdlib"))
    implementation(getDependency("coreKtx"))
    implementation(getDependency("lifecycleRuntimeKtx"))

    // ui
    implementation(getDependency("appcompat"))
    implementation(getDependency("constraintlayout"))
    implementation(getDependency("material"))
    implementation(getDependency("rightBottomSheet"))

    // Koin for Android
    implementation(getDependency("KoinAndroid"))
    implementation(getDependency("koinAnnotations"))
    ksp(getDependency("koinKspCompiler"))

    // activity
    implementation(getDependency("activityKtx"))
    implementation(getDependency("fragmentKtx"))

    // DataStore
    implementation(getDependency("datastore"))

    // Picasso
    implementation(getDependency("picasso"))

    // Serialization
    implementation(getDependency("serialization"))
    implementation(getDependency("serializationConverter"))
    implementation(getDependency("datetime"))

    // splash screen
    implementation(getDependency("splashscreen"))

    // Pagging
    testImplementation(getDependency("pagingCommonKtx"))
    implementation(getDependency("pagingRuntimeKtx"))

    // Navigation
    implementation(getDependency("navigationFragmentKtx"))
    implementation(getDependency("navigationUiKtx"))

    // realm
    implementation(getDependency("realm"))

    // coroutine
    implementation(getDependency("coroutinesAndroid"))
    implementation(getDependency("coroutinesCore"))

    // okhttp
    implementation(getDependency("okhttp"))
    implementation(getDependency("okhttpLoggingInterceptor"))

    // retrofit
    implementation(getDependency("retrofit"))

    // Facebook shimmer
    implementation(getDependency("shimmer"))

    // Lottie
    implementation(getDependency("lottie"))

    // Leak Canary
    // debugImplementation(getDependency("leakCanary"))

    // tests
    testImplementation(getDependency("junit"))
    testImplementation(getDependency("mockk"))
    testImplementation(getDependency("robolectric"))
    testImplementation(getDependency("testCore"))
    testImplementation(getDependency("koinTest"))
    testImplementation(getDependency("koinTestJunit4"))
    testImplementation(getDependency("kluent"))
    testImplementation(getDependency("kluentAndroid"))
    testImplementation(getDependency("kotlinTestJunit"))
    testImplementation(getDependency("coroutinesTest"))
    testImplementation(getDependency("turbine"))
    // instrumentation test
    debugImplementation(
        getDependency("fragmentTesting")
    ) {
        exclude(group = "androidx.test", module = "core")
    }
    androidTestImplementation(getDependency("espressoContrib"))
    androidTestImplementation(getDependency("runner"))
    androidTestImplementation(getDependency("espressoIntents"))
    androidTestImplementation(getDependency("rules"))
    androidTestImplementation(getDependency("extJunitKtx"))
    androidTestImplementation(getDependency("extTruth"))
    androidTestImplementation(getDependency("espressoCore"))
}

typealias ReporterType = org.jlleitschuh.gradle.ktlint.reporter.ReporterType

ktlint {
    version.set("0.43.1")
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    disabledRules.set(
        setOf(
            "final-newline",
            "import-ordering",
            "max-line-length",
            "experimental:argument-list-wrapping"
        )
    )
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
