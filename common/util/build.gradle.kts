plugins {
    id("com.maksimbagalei.library")
}

dependencies {
    implementation(libs.androidx.paging.compose)

    testImplementation(project(":common:test"))
}