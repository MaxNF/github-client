plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":feature:user-details:domain"))
    implementation(project(":feature:user-details:data"))
    implementation(project(":common:ui"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.compose.tooling.preview)
    implementation(libs.shimmer.compose)
    implementation(libs.coil)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.hilt.navigation.compose)
    testImplementation(project(":common:test"))
    debugImplementation(libs.androidx.ui.tooling)
}