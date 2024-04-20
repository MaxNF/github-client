plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    testImplementation(project(":common:test"))

    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.serialization.converter)
}