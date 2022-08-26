plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    `groovy-gradle-plugin`
}
// We set up group and version to make this project available as dependency artifact
group = "com.example.sandbox.buildlogic"
version = "0.1"

@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
dependencies {
    implementation(libs.androidGradlePlugin)
    implementation(libs.kotlinGradlePlugin)
    implementation(libs.versionsPlugin)
}

gradlePlugin {
    plugins {
        register("dependencyUpdater") {
            id = "sandbox.android.dependencyUpdater"
            implementationClass = "DependencyUpdatePlugin"
        }
        register("sandboxJacoco") {
            id = "sandbox.android.sandboxJacoco"
            implementationClass = "JacocoPlugin"
        }
    }
}
