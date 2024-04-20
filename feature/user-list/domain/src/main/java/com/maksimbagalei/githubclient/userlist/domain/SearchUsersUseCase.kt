package com.maksimbagalei.githubclient.userlist.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.data.paging.UserListPagingSource
import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 100

class SearchUsersUseCase @Inject constructor(private val repository: UserListRepository) {

    operator fun invoke(name: String): Flow<PagingData<UserBrief>> =
        Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 50,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            )
        ) {
            UserListPagingSource(repository = repository, searchName = name)
        }.flow
}