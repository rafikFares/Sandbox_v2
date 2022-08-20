buildscript {
    val compileSdkVersion = 32
    val targetSdkVersion = compileSdkVersion
    val minSdkVersion = 24
    val versionCode = 1
    val versionName = "0.4"
    val koin_version = "3.2.0"
    val koin_ksp_version = "1.0.1"
    val kotlin_version = "1.7.10"
    val core_version = "1.8.0"
    val lifecycle_version = "2.5.1"
    val appcompat_version = "1.5.0"
    val constraintlayout_version = "2.1.4"
    val material_version = "1.6.1"
    val coroutines_version = "1.6.4"
    val datastore_version = "1.0.0"
    val picasso_version = "2.8"
    val serialization_version = "1.3.3"
    val converter_version = "0.8.0"
    val splashscreen_version = "1.0.0"
    val okhttp_version = "4.10.0"
    val retrofit_version = "2.9.0"
    val datetime_version = "0.4.0"
    val shimmer_version = "0.5.0"
    val lottie_version = "5.2.0"
    val leakCanary_version = "2.9.1"
    val realm_version = "1.0.2"
    val paging_version = "3.1.1"
    val nav_version = "2.5.1"
    val rightBottomSheet_version = "1.0"
    val junit_version = "4.13.2"
    val mockk_version = "1.12.1"
    val robolectric_version = "4.8.1"
    val testCore_version = "1.4.0"
    val koinTest_version = "3.2.0"
    val kluent_version = "1.68"
    val kotlinTest_version = "1.7.10"
    val coroutinesTest_version = "1.6.4"
    val turbine_version = "0.9.0"
    val fragmentTesting_version = "1.6.0-alpha01"
    val espresso_version = "3.5.0-alpha07"
    val runner_version = "1.5.0-alpha04"
    val rules_version = "1.4.1-alpha07"
    val extJunit_version = "1.1.4-alpha07"
    val extTruth_version = "1.5.0-alpha07"
    val activityKtx_version = "1.5.1"
    val fragmentKtx_version = "1.5.2"

    extra.apply {
        set("compileSdkVersion", compileSdkVersion)
        set("targetSdkVersion", targetSdkVersion)
        set("minSdkVersion", minSdkVersion)
        set("versionCode", versionCode)
        set("versionName", versionName)
        set("KoinAndroid", "io.insert-koin:koin-android:$koin_version")
        set("koinAnnotations", "io.insert-koin:koin-annotations:$koin_ksp_version")
        set("koinKspCompiler", "io.insert-koin:koin-ksp-compiler:$koin_ksp_version")
        set("kotlinStdlib", "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
        set("coreKtx", "androidx.core:core-ktx:$core_version")
        set("testCore", "androidx.test:core:$testCore_version")
        set("lifecycleRuntimeKtx", "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
        set("appcompat", "androidx.appcompat:appcompat:$appcompat_version")
        set(
            "constraintlayout",
            "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
        )
        set("material", "com.google.android.material:material:$material_version")
        set(
            "coroutinesAndroid",
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
        )
        set("coroutinesCore", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
        set("datastore", "androidx.datastore:datastore-preferences:$datastore_version")
        set("picasso", "com.squareup.picasso:picasso:$picasso_version")
        set(
            "serialization",
            "org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version"
        )
        set(
            "serializationConverter",
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$converter_version"
        )
        set("splashscreen", "androidx.core:core-splashscreen:$splashscreen_version")
        set("okhttp", "com.squareup.okhttp3:okhttp:$okhttp_version")
        set("okhttpLoggingInterceptor", "com.squareup.okhttp3:logging-interceptor:$okhttp_version")
        set("retrofit", "com.squareup.retrofit2:retrofit:$retrofit_version")
        set("datetime", "org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version")
        set("shimmer", "com.facebook.shimmer:shimmer:$shimmer_version")
        set("lottie", "com.airbnb.android:lottie:$lottie_version")
        set("leakCanary", "com.squareup.leakcanary:leakcanary-android:$leakCanary_version")
        set("realm", "io.realm.kotlin:library-base:$realm_version")
        set("pagingCommonKtx", "androidx.paging:paging-common-ktx:$paging_version")
        set("pagingRuntimeKtx", "androidx.paging:paging-runtime-ktx:$paging_version")
        set("navigationFragmentKtx", "androidx.navigation:navigation-fragment-ktx:$nav_version")
        set("navigationUiKtx", "androidx.navigation:navigation-ui-ktx:$nav_version")
        set("rightBottomSheet", "com.github.OKatrych:RightSheetBehavior:$rightBottomSheet_version")
        set("junit", "junit:junit:$junit_version")
        set("mockk", "io.mockk:mockk:$mockk_version")
        set("robolectric", "org.robolectric:robolectric:$robolectric_version")
        set("testCore", "androidx.test:core:$testCore_version")
        set("koinTest", "io.insert-koin:koin-test:$koinTest_version")
        set("koinTestJunit4", "io.insert-koin:koin-test-junit4:$koinTest_version")
        set("kluent", "org.amshove.kluent:kluent:$kluent_version")
        set("kluentAndroid", "org.amshove.kluent:kluent-android:$kluent_version")
        set("kotlinTestJunit", "org.jetbrains.kotlin:kotlin-test-junit:$kotlinTest_version")
        set(
            "coroutinesTest",
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTest_version"
        )
        set("turbine", "app.cash.turbine:turbine:$turbine_version")
        set("fragmentTesting", "androidx.fragment:fragment-testing:$fragmentTesting_version")
        set("espressoContrib", "androidx.test.espresso:espresso-contrib:$espresso_version")
        set("espressoIntents", "androidx.test.espresso:espresso-intents:$espresso_version")
        set("espressoCore", "androidx.test.espresso:espresso-core:$espresso_version")
        set("runner", "androidx.test:runner:$runner_version")
        set("rules", "androidx.test:rules:$rules_version")
        set("extJunitKtx", "androidx.test.ext:junit-ktx:$extJunit_version")
        set("extTruth", "androidx.test.ext:truth:$extTruth_version")
        set("activityKtx", "androidx.activity:activity-ktx:$activityKtx_version")
        set("fragmentKtx", "androidx.fragment:fragment-ktx:$fragmentKtx_version")
    }
}
plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    id("io.realm.kotlin") version "1.0.2" apply false
    id("org.jetbrains.kotlin.kapt") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    id("org.jetbrains.kotlin.jvm") version "1.7.10" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
