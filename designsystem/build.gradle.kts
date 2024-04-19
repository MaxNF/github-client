plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
    id("com.maksimbagalei.compose.library")

}

dependencies {
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.tooling.preview)
}