plugins {
    id("com.maksimbagalei.library")
}

dependencies {
    api(libs.junit)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
    api(libs.androidx.core.testing)
    api(libs.androidx.junit)
    api(kotlin("test"))
}