plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":feature:user-list:data"))

    testImplementation(project(":common:test"))
}