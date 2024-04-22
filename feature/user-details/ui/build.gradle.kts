plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
    id("com.maksimbagalei.compose.library")
}

dependencies {
    implementation(project(":feature:user-details:domain"))
    implementation(project(":feature:user-details:data"))
    implementation(project(":common:ui"))
    implementation(project(":common:data"))
    implementation(project(":common:util"))
    implementation(project(":designsystem"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.shimmer.compose)
    implementation(libs.coil)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.androidx.browser)
    testImplementation(project(":common:test"))
    debugImplementation(libs.androidx.ui.tooling)
}