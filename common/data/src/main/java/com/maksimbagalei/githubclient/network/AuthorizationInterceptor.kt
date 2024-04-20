package com.maksimbagalei.githubclient.network

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY =
    "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"

internal class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer $API_KEY"
            ).build()
        return chain.proceed(newRequest)
    }
}