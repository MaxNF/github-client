package com.maksimbagalei.githubclient.userlist.domain

import androidx.paging.PagingData
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.data.paging.UserListPagerFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val userListPagerFactory: UserListPagerFactory) {

    operator fun invoke(name: String): Flow<PagingData<UserBrief>> =
        userListPagerFactory.createPager(name).flow
}