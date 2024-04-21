package com.maksimbagalei.githubclient.userdetails.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.paging.UserRepositoriesPagingSource
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PAGE_SIZE = 100

class GetNotForkedRepositoriesUseCase @Inject constructor(private val repository: UserDetailsRepository) {

    operator fun invoke(login: String): Flow<PagingData<Repository>> =
        Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = true
            )
        ) {
            UserRepositoriesPagingSource(repository = repository, login = login)
        }.flow.map { pagingData ->
            pagingData.filter {
                !it.isFork
            }
        }
}