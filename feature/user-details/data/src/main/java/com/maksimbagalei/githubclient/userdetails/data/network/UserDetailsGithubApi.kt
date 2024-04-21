package com.maksimbagalei.githubclient.userdetails.data.network

import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails
import retrofit2.Retrofit
import retrofit2.http.GET

internal interface UserDetailsGithubApi {
    companion object {
        fun create(retrofit: Retrofit): UserDetailsGithubApi =
            retrofit.create(UserDetailsGithubApi::class.java)
    }

    @GET("/search/users")
    suspend fun fetchUserDetails(

    ): UserDetails
}