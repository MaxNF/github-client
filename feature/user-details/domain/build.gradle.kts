plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":feature:user-details:data"))

    implementation(libs.androidx.paging.compose)
    testImplementation(project(":common:test"))
}