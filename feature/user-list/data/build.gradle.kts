plugins {
    id("com.maksimbagalei.library")
    id("com.maksimbagalei.hilt")
}

dependencies {
    implementation(project(":common"))

    testImplementation(project(":common-test"))
}