plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":common:data"))

    implementation(libs.retrofit)
    implementation(libs.retrofit2.serialization.converter)
    implementation(libs.androidx.paging.compose)
    testImplementation(project(":common:test"))
}