plugins {
    id("com.maksimbagalei.library")
}

dependencies {
    api(libs.junit)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
    api(libs.androidx.core.testing)
    api(libs.androidx.junit)
    api(libs.androidx.paging.testing)
    api(libs.androidx.paging.runtime.ktx)
    api(kotlin("test"))
}