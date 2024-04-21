package com.maksimbagalei.githubclient.userdetails.data.repo.impl

import com.maksimbagalei.githubclient.data.repo.BaseRepo
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.dto.UserDetails
import com.maksimbagalei.githubclient.userdetails.data.network.UserDetailsGithubApi
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import javax.inject.Inject

internal class UserDetailsRepositoryImpl @Inject constructor(private val api: UserDetailsGithubApi) :
    BaseRepo(), UserDetailsRepository {

    override suspend fun getUserDetails(login: String): CallResult<UserDetails> = safeApiCall {
        api.getUserDetails(login)
    }

    override suspend fun getUserRepos(login: String, page: Int, perPage: Int): CallResult<List<Repository>> = safeApiCall {
        api.getUserRepos(login, page, perPage)
    }
}