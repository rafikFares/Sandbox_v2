// Classpaths versioning
plugins {
    id(BuildPlugins.application) version BuildPlugins.applicationVersion apply false
    id(BuildPlugins.library) version BuildPlugins.libraryVersion apply false
    id(BuildPlugins.kotlinAndroid) version Libs.Common.kotlinVersion apply false
    id(BuildPlugins.ksp) version BuildPlugins.kspVersion apply false
    id(BuildPlugins.realmKotlin) version Libs.Common.realmVersion apply false
    kotlin(BuildPlugins.kapt) version Libs.Common.kotlinVersion apply false
    kotlin(BuildPlugins.serialization) version Libs.Common.kotlinVersion apply false
    kotlin(BuildPlugins.kotlinJvm) version Libs.Common.kotlinVersion apply false
    id(BuildPlugins.ktlint) version BuildPlugins.ktLintVersion apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
