package com.maksimbagalei.githubclient.userlist.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository
import javax.inject.Inject

private const val PAGE_SIZE = 100

class UserListPagerFactory @Inject constructor(private val repo: UserListRepository) {

    fun createPager(searchName: String) = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PAGE_SIZE / 2,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = true
        )
    ) {
        UserListPagingSource(repository = repo, searchName = searchName)
    }
}