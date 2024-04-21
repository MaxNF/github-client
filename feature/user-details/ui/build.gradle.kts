plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":feature:user-details:domain"))
    implementation(project(":common:ui"))

    testImplementation(project(":common:test"))
}