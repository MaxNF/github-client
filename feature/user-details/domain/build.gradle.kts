plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":feature:user-details:data"))
    implementation(project(":common:data"))

    implementation(libs.androidx.paging.compose)
    testImplementation(project(":common:test"))
    testImplementation(libs.androidx.paging.testing)
    testImplementation("androidx.paging:paging-runtime-ktx:3.2.1")
}