package com.maksimbagalei.githubclient

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project

internal fun configureAndroidLibrary(
    libraryExtension: LibraryExtension
) {
    with(libraryExtension) {
        namespace = "com.maksimbagalei.githubclient"
        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
}

internal fun Project.addLibraryPlugins() {
    with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
        apply("org.jetbrains.kotlin.plugin.serialization")
        apply("org.jetbrains.kotlin.plugin.parcelize")
    }
}