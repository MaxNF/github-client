package com.maksimbagalei.githubclient.userdetails.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import javax.inject.Inject

private const val PAGE_SIZE = 100

class UserRepositoriesPagerFactory @Inject constructor(private val repo: UserDetailsRepository) {

    fun createPager(login: String) = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PAGE_SIZE / 2,
            initialLoadSize = PAGE_SIZE,
            enablePlaceholders = true
        )
    ) {
        UserRepositoriesPagingSource(repository = repo, login = login)
    }
}
