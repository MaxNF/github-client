package com.maksimbagalei.githubclient.userlist.data.network

import com.maksimbagalei.githubclient.userlist.data.dto.UserList
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UserListGithubApi {
    companion object {
        fun create(retrofit: Retrofit): UserListGithubApi =
            retrofit.create(UserListGithubApi::class.java)
    }

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserList
}