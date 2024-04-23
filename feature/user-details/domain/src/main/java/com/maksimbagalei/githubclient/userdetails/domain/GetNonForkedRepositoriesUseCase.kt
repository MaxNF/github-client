package com.maksimbagalei.githubclient.userdetails.domain

import androidx.paging.PagingData
import androidx.paging.filter
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.paging.UserRepositoriesPagerFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNonForkedRepositoriesUseCase @Inject constructor(private val factory: UserRepositoriesPagerFactory) {

    operator fun invoke(login: String): Flow<PagingData<Repository>> =
        factory.createPager(login).flow.map { pagingData ->
            pagingData.filter {
                !it.isFork
            }
        }
}