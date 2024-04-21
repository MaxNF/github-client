package com.maksimbagalei.githubclient.userdetails.data.repo

import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails

interface UserDetailsRepository {

    suspend fun getUserDetails(login: String): CallResult<UserDetails>

    suspend fun getUserRepos(login: String, page: Int, perPage: Int): CallResult<List<Repository>>
}