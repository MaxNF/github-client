package com.maksimbagalei.githubclient.userlist.data.repo

import com.maksimbagalei.githubclient.userlist.data.CallResult
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief

interface UserListRepository {

    suspend fun searchUsers(name: String, page: Int, perPage: Int): CallResult<List<UserBrief>>
}