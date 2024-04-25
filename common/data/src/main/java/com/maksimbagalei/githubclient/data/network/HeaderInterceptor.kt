package com.maksimbagalei.githubclient.data.network

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY = ""

private const val AUTH_NAME = "Authorization"
private const val AUTH_VALUE = "Bearer $API_KEY"

private const val API_VERSION_NAME = "X-GitHub-Api-Version"
private const val API_VERSION_VALUE = "2022-11-28"

private const val ACCEPT_NAME = "Accept"
private const val ACCEPT_VALUE = "application/vnd.github+json"

internal class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (API_KEY.isNotBlank()) builder.addHeader(AUTH_NAME, AUTH_VALUE)
            builder.addHeader(API_VERSION_NAME, API_VERSION_VALUE)
            builder.addHeader(ACCEPT_NAME, ACCEPT_VALUE)

        return chain.proceed(builder.build())
    }
}