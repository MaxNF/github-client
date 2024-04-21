package com.maksimbagalei.githubclient.userdetails.data.network

import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface UserDetailsGithubApi {
    companion object {
        fun create(retrofit: Retrofit): UserDetailsGithubApi =
            retrofit.create(UserDetailsGithubApi::class.java)
    }

    @GET("/users/{login}")
    suspend fun getUserDetails(@Path("login") login: String): UserDetails

    @GET("/users/{login}/repos")
    suspend fun getUserRepos(
        @Path("login") login: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Repository>
}