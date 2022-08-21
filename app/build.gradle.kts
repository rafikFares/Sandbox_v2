import Build_gradle.ReporterType

plugins {
    // Application Plugins
    id(BuildPlugins.application)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.ksp)
    id(BuildPlugins.realmKotlin)
    kotlin(BuildPlugins.kapt)
    kotlin(BuildPlugins.serialization)
    id(BuildPlugins.ktlint)
}

android {

    signingConfigs {
        create("release") {
            storeFile = file(KeyStore.keyStorePath)
            storePassword = KeyStore.keyStorePassword
            keyAlias = KeyStore.keyStoreAlias
            keyPassword = KeyStore.keyStoreKeyPassword
        }
        getByName("debug") {
            storeFile = file(KeyStore.keyStorePath)
            storePassword = KeyStore.keyStorePassword
            keyAlias = KeyStore.keyStoreAlias
            keyPassword = KeyStore.keyStoreKeyPassword
        }
    }

    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.example.sandbox"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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

    // Kotlin
    implementation(Libs.Default.kotlinStdlib)
    implementation(Libs.Default.coreKtx)
    implementation(Libs.Default.lifecycleRuntimeKtx)

    // ui
    implementation(Libs.Default.appcompat)
    implementation(Libs.Default.constraintlayout)
    implementation(Libs.Default.material)
    implementation(Libs.Default.rightBottomSheet)

    // Koin for Android
    implementation(Libs.Di.KoinAndroid)
    implementation(Libs.Di.koinAnnotations)
    ksp(Libs.Di.koinKspCompiler)

    // activity
    implementation(Libs.Default.activityKtx)
    implementation(Libs.Default.fragmentKtx)

    // DataStore
    implementation(Libs.Default.datastore)

    // Picasso
    implementation(Libs.Default.picasso)

    // Serialization
    implementation(Libs.Default.serialization)
    implementation(Libs.Default.serializationConverter)
    implementation(Libs.Default.datetime)

    // splash screen
    implementation(Libs.Default.splashscreen)

    // Pagging
    testImplementation(Libs.Default.pagingCommonKtx)
    implementation(Libs.Default.pagingRuntimeKtx)

    // Navigation
    implementation(Libs.Default.navigationFragmentKtx)
    implementation(Libs.Default.navigationUiKtx)

    // realm
    implementation(Libs.Default.realm)

    // coroutine
    implementation(Libs.Default.coroutinesAndroid)
    implementation(Libs.Default.coroutinesCore)

    // okhttp
    implementation(Libs.Default.okhttp)
    implementation(Libs.Default.okhttpLoggingInterceptor)

    // retrofit
    implementation(Libs.Default.retrofit)

    // Facebook shimmer
    implementation(Libs.Default.shimmer)

    // Lottie
    implementation(Libs.Default.lottie)

    // Leak Canary
    // debugImplementation(Libs.Dev.leakCanary)

    // tests
    testImplementation(Libs.Test.junit)
    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.robolectric)
    testImplementation(Libs.Test.testCore)
    testImplementation(Libs.Test.koinTest)
    testImplementation(Libs.Test.koinTestJunit4)
    testImplementation(Libs.Test.kluent)
    testImplementation(Libs.Test.kluentAndroid)
    testImplementation(Libs.Test.kotlinTestJunit)
    testImplementation(Libs.Test.coroutinesTest)
    testImplementation(Libs.Test.turbine)
    // instrumentation test
    debugImplementation(
        Libs.AndroidTest.fragmentTesting
    ) {
        exclude(group = "androidx.test", module = "core")
    }
    androidTestImplementation(Libs.AndroidTest.espressoContrib)
    androidTestImplementation(Libs.AndroidTest.runner)
    androidTestImplementation(Libs.AndroidTest.espressoIntents)
    androidTestImplementation(Libs.AndroidTest.rules)
    androidTestImplementation(Libs.AndroidTest.extJunitKtx)
    androidTestImplementation(Libs.AndroidTest.extTruth)
    androidTestImplementation(Libs.AndroidTest.espressoCore)
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
