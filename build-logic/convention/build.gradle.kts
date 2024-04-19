import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.maksimbagalei.githubclient.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.maksimbagalei.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidHilt") {
            id = "com.maksimbagalei.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.maksimbagalei.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidComposeApplication") {
            id = "com.maksimbagalei.compose.application"
            implementationClass = "AndroidComposeApplicationConventionPlugin"
        }
        register("androidComposeLibrary") {
            id = "com.maksimbagalei.compose.library"
            implementationClass = "AndroidComposeLibraryConventionPlugin"
        }
    }
}