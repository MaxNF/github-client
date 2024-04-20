package com.maksimbagalei.githubclient.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maksimbagalei.githubclient.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

private const val GITHUB_API_BASE_URL = "https://api.github.com"

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .build()

    @Suppress("JSON_FORMAT_REDUNDANT")
    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        val converterFactory = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        }.asConverterFactory("application/json".toMediaType())
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GITHUB_API_BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }
}