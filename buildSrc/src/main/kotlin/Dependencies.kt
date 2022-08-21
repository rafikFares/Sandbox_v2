import Libs.Common.kotlinVersion
import Libs.Common.realmVersion

object ScriptPlugins {
    const val gradleVersion = "7.2.2"
    const val infrastructure = "scripts.infrastructure"
    const val variants = "scripts.variants"
    const val quality = "scripts.quality"
    const val compilation = "scripts.compilation"
}

object KeyStore {
    const val keyStorePath = "key/debug.keystore"
    const val keyStorePassword = "android"
    const val keyStoreAlias = "androiddebugkey"
    const val keyStoreKeyPassword = "android"
}

object ConfigData {
    const val compileSdkVersion = 32
    const val targetSdkVersion = compileSdkVersion
    const val minSdkVersion = 24
    const val versionCode = 1
    const val versionName = "0.4.1"
}

/**
 * To define plugins
 */
object BuildPlugins {
    const val applicationVersion = "7.2.2"
    const val libraryVersion = "7.2.2"
    const val kspVersion = "1.7.10-1.0.6"
    const val ktLintVersion = "10.3.0"

    val application by lazy { "com.android.application" }
    val library by lazy { "com.android.library" }
    val kotlinAndroid by lazy { "org.jetbrains.kotlin.android" }
    val ksp by lazy { "com.google.devtools.ksp" }
    val realmKotlin by lazy { "io.realm.kotlin" }
    val kapt by lazy { "kapt" }
    val serialization by lazy { "plugin.serialization" }
    val ktlint by lazy { "org.jlleitschuh.gradle.ktlint" }
    val kotlinJvm by lazy { "jvm" }
    val kotlinPlugin by lazy { "gradle-plugin" }
}

/**
 * To define dependencies
 */
object Libs {

    object Common {
        const val kotlinVersion = "1.7.10"
        const val realmVersion = "1.0.2"
    }

    object Default {
        private const val core_version = "1.8.0"
        private const val lifecycle_version = "2.5.1"
        private const val appcompat_version = "1.5.0"
        private const val constraintlayout_version = "2.1.4"
        private const val material_version = "1.6.1"
        private const val coroutines_version = "1.6.4"
        private const val datastore_version = "1.0.0"
        private const val picasso_version = "2.8"
        private const val serialization_version = "1.3.3"
        private const val converter_version = "0.8.0"
        private const val splashscreen_version = "1.0.0"
        private const val okhttp_version = "4.10.0"
        private const val retrofit_version = "2.9.0"
        private const val datetime_version = "0.4.0"
        private const val shimmer_version = "0.5.0"
        private const val lottie_version = "5.2.0"
        private const val activityKtx_version = "1.5.1"
        private const val fragmentKtx_version = "1.5.2"
        private const val paging_version = "3.1.1"
        private const val nav_version = "2.5.1"
        private const val rightBottomSheet_version = "1.0"

        val kotlinStdlib by lazy { "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion" }
        val coreKtx by lazy { "androidx.core:core-ktx:$core_version" }
        val lifecycleRuntimeKtx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version" }
        val appcompat by lazy { "androidx.appcompat:appcompat:$appcompat_version" }
        val constraintlayout by lazy { "androidx.constraintlayout:constraintlayout:$constraintlayout_version" }
        val material by lazy { "com.google.android.material:material:$material_version" }
        val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version" }
        val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version" }
        val datastore by lazy { "androidx.datastore:datastore-preferences:$datastore_version" }
        val picasso by lazy { "com.squareup.picasso:picasso:$picasso_version" }
        val serialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version" }
        val serializationConverter by lazy { "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$converter_version" }
        val splashscreen by lazy { "androidx.core:core-splashscreen:$splashscreen_version" }
        val okhttp by lazy { "com.squareup.okhttp3:okhttp:$okhttp_version" }
        val okhttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:$okhttp_version" }
        val retrofit by lazy { "com.squareup.retrofit2:retrofit:$retrofit_version" }
        val datetime by lazy { "org.jetbrains.kotlinx:kotlinx-datetime:$datetime_version" }
        val shimmer by lazy { "com.facebook.shimmer:shimmer:$shimmer_version" }
        val lottie by lazy { "com.airbnb.android:lottie:$lottie_version" }
        val realm by lazy { "io.realm.kotlin:library-base:$realmVersion" }
        val pagingCommonKtx by lazy { "androidx.paging:paging-common-ktx:$paging_version" }
        val pagingRuntimeKtx by lazy { "androidx.paging:paging-runtime-ktx:$paging_version" }
        val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:$nav_version" }
        val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:$nav_version" }
        val rightBottomSheet by lazy { "com.github.OKatrych:RightSheetBehavior:$rightBottomSheet_version" }
        val activityKtx by lazy { "androidx.activity:activity-ktx:$activityKtx_version" }
        val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:$fragmentKtx_version" }
    }

    object Di {
        private const val koin_version = "3.2.0"
        private const val koin_ksp_version = "1.0.1"

        val KoinAndroid by lazy { "io.insert-koin:koin-android:$koin_version" }
        val koinAnnotations by lazy { "io.insert-koin:koin-annotations:$koin_ksp_version" }
        val koinKspCompiler by lazy { "io.insert-koin:koin-ksp-compiler:$koin_ksp_version" }
    }

    object Dev {
        private const val leakCanary_version = "2.9.1"

        val leakCanary by lazy { "com.squareup.leakcanary:leakcanary-android:$leakCanary_version" }
    }

    object Test {
        private const val junit_version = "4.13.2"
        private const val mockk_version = "1.12.1"
        private const val robolectric_version = "4.8.1"
        private const val testCore_version = "1.4.0"
        private const val koinTest_version = "3.2.0"
        private const val kluent_version = "1.68"
        private const val coroutinesTest_version = "1.6.4"
        private const val turbine_version = "0.9.0"

        val junit by lazy { "junit:junit:$junit_version" }
        val mockk by lazy { "io.mockk:mockk:$mockk_version" }
        val robolectric by lazy { "org.robolectric:robolectric:$robolectric_version" }
        val testCore by lazy { "androidx.test:core:$testCore_version" }
        val koinTest by lazy { "io.insert-koin:koin-test:$koinTest_version" }
        val koinTestJunit4 by lazy { "io.insert-koin:koin-test-junit4:$koinTest_version" }
        val kluent by lazy { "org.amshove.kluent:kluent:$kluent_version" }
        val kluentAndroid by lazy { "org.amshove.kluent:kluent-android:$kluent_version" }
        val kotlinTestJunit by lazy { "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion" }
        val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesTest_version" }
        val turbine by lazy { "app.cash.turbine:turbine:$turbine_version" }
    }

    object AndroidTest {
        private const val fragmentTesting_version = "1.6.0-alpha01"
        private const val espresso_version = "3.5.0-alpha07"
        private const val runner_version = "1.5.0-alpha04"
        private const val rules_version = "1.4.1-alpha07"
        private const val extJunit_version = "1.1.4-alpha07"
        private const val extTruth_version = "1.5.0-alpha07"

        val fragmentTesting by lazy { "androidx.fragment:fragment-testing:$fragmentTesting_version" }
        val espressoContrib by lazy { "androidx.test.espresso:espresso-contrib:$espresso_version" }
        val espressoIntents by lazy { "androidx.test.espresso:espresso-intents:$espresso_version" }
        val espressoCore by lazy { "androidx.test.espresso:espresso-core:$espresso_version" }
        val runner by lazy { "androidx.test:runner:$runner_version" }
        val rules by lazy { "androidx.test:rules:$rules_version" }
        val extJunitKtx by lazy { "androidx.test.ext:junit-ktx:$extJunit_version" }
        val extTruth by lazy { "androidx.test.ext:truth:$extTruth_version" }
    }
}
