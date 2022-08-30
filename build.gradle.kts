buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.androidGradlePlugin)
        classpath(libs.kotlinGradlePlugin)
        classpath(libs.kotlinSerializationPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<Test>().configureEach {
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

// tasks.withType<Test>().configureEach {
//     if (name == "connectedDebugAndroidTest") {
//         ignoreFailures = true
//     }
// }
