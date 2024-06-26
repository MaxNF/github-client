plugins {
    id("com.maksimbagalei.application")
    id("com.maksimbagalei.hilt")
    id("com.maksimbagalei.compose.application")
}

android {
    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(project(":designsystem"))
    implementation(project(":feature:user-list:ui"))
    implementation(project(":feature:user-details:ui"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.navigation.compose)
}