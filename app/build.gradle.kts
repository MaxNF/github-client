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
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.tooling.preview)
}