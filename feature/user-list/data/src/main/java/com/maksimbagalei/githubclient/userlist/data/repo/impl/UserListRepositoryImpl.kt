package com.maksimbagalei.githubclient.userlist.data.repo.impl

import com.maksimbagalei.githubclient.repo.BaseRepo
import com.maksimbagalei.githubclient.repo.CallResult
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.data.network.UserListGithubApi
import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository
import javax.inject.Inject

internal class UserListRepositoryImpl @Inject constructor(private val api: UserListGithubApi) : BaseRepo(),
    UserListRepository {
    override suspend fun searchUsers(name: String, page: Int, perPage: Int): CallResult<List<UserBrief>> = safeApiCall {
        api.searchUsers(
            query = "$name+in:name",
            type = "users",
            page = page,
            perPage = perPage
        )
    }
}