import com.android.build.api.dsl.ApplicationExtension
import com.maksimbagalei.githubclient.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.kotlin.plugin.parcelize")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                namespace = "com.maksimbagalei.githubclient"
                buildFeatures {
                    buildConfig = true
                }
                defaultConfig {
                    targetSdk = 34
                    applicationId = "com.maksimbagalei.githubclient"
                    testInstrumentationRunner =
                        "androidx.test.runner.AndroidJUnitRunner"
                }
                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}