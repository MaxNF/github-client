plugins {
    id("com.maksimbagalei.library")
}

dependencies {
    implementation(project(":common"))

    api(libs.junit)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
    api(libs.androidx.core.testing)
    api(libs.androidx.junit)
}