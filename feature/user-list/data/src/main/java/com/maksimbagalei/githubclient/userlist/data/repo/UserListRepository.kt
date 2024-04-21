package com.maksimbagalei.githubclient.userlist.data.repo

import com.maksimbagalei.githubclient.repo.CallResult
import com.maksimbagalei.githubclient.userlist.data.dto.UserList

interface UserListRepository {

    suspend fun searchUsers(name: String, page: Int, perPage: Int): CallResult<UserList>
}