plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":feature:user-list:domain"))

    testImplementation(project(":common:test"))
}