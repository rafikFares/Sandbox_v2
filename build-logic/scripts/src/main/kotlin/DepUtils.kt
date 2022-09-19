import org.gradle.api.JavaVersion

object DepUtils {
    object KeyStore {
        const val keyStorePath = "key/debug.keystore"
        const val keyStorePassword = "android"
        const val keyStoreAlias = "androiddebugkey"
        const val keyStoreKeyPassword = "android"
    }

    object ConfigData {
        const val compileSdkVersion = 32
        const val targetSdkVersion = 32
        const val minSdkVersion = 24
        const val versionCode = 1
        const val versionName = "0.6"
        const val applicationId = "com.example.sandbox"
    }

    object Java {
        val sourceCompatibility by lazy { JavaVersion.VERSION_1_8 }
        val targetCompatibility by lazy { JavaVersion.VERSION_1_8 }
        val jvmTarget by lazy { "${JavaVersion.VERSION_1_8}" }
    }
}
