import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.gradle.internal.tasks.factory.dependsOn
import gradle.kotlin.dsl.accessors._9bc8e58b7d586f485ad10a9fed6954a3.android
import gradle.kotlin.dsl.accessors._9bc8e58b7d586f485ad10a9fed6954a3.androidTestImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

private const val jacocoVersion = "0.8.7"

class JacocoPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.gradle.jacoco")
                apply("com.android.application")
            }
            val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
            configureJacoco(extension)
        }
    }
}

private fun Project.configureJacoco(
    androidComponentsExtension: AndroidComponentsExtension<*, *, *>
) {
    android {
        buildTypes {
            debug {
                enableUnitTestCoverage = true
            }
        }
    }

    configure<JacocoPluginExtension> {
        toolVersion = jacocoVersion
    }

    val jacocoTestReport = tasks.register("jacocoTestReport") {
        group = "Quality"
        description = "Report code coverage on tests within the Android codebase."
    }

    val outputDir = "${project.buildDir}/testCoverage"

    androidComponentsExtension.onVariants { variant ->
        val testTaskName = "test${variant.name.capitalize()}UnitTest"

        val reportTask = tasks.register("jacoco${testTaskName.capitalize()}Report", JacocoReport::class) {
            group = "Quality"
            description = "Report code coverage on tests within the Android codebase."
            dependsOn(testTaskName)

            reports {
                xml.required.set(true)
                html.required.set(true)
                html.outputLocation.set(file(outputDir))
            }

            classDirectories.setFrom(
                fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                    exclude(coverageExclusionsDefault)
                }
            )

            sourceDirectories.setFrom(files("$projectDir/src/main/java", "$projectDir/src/main/kotlin"))
            executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
            doLast { println("Code Coverage Report: $outputDir/index.html") }
        }

        jacocoTestReport.dependsOn(reportTask)
    }

    tasks.withType<Test>().configureEach {
        configure<JacocoTaskExtension> {
            // Required for JaCoCo + Robolectric
            // https://github.com/robolectric/robolectric/issues/2230
            // Whether or not classes without source location should be instrumented
            isIncludeNoLocationClasses = true

            // Required for JDK 11 with the above
            // https://github.com/gradle/gradle/issues/5184#issuecomment-391982009
            excludes = listOf("jdk.internal.*")
        }
    }

    dependencies {
        androidTestImplementation("org.jacoco:org.jacoco.agent:$jacocoVersion:runtime") // to fix Jacoco missing jars ?
    }
}

private val coverageExclusionsDefault = listOf(
    // Android
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*"
)

// can't use this ones because it broke androidTests
private val coverageExclusions = listOf(
    // data binding
    "android/databinding/**/*.class",
    "**/android/databinding/*Binding.class",
    "**/android/databinding/*",
    "**/androidx/databinding/*",
    "**/BR.*",
    // android
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    // kotlin
    "**/*MapperImpl*.*",
    "**/*ViewInjector*.*",
    "**/*ViewBinder*.*",
    "**/BuildConfig.*",
    "**/*Component*.*",
    "**/*BR*.*",
    "**/Manifest*.*",
    "**/*Extensions*.*",
    "**/*Module*.*",
    // ksp
    "**/ksp.*",
    "**/koin.*"
)
