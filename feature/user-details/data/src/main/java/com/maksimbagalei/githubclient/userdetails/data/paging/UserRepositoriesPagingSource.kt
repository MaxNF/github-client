package com.maksimbagalei.githubclient.userdetails.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maksimbagalei.githubclient.data.repo.CallResult
import com.maksimbagalei.githubclient.userdetails.data.dto.Repository
import com.maksimbagalei.githubclient.userdetails.data.repo.UserDetailsRepository

private const val STARTING_PAGE = 1
private const val MIN_LOAD_SIZE = 1
private const val MAX_LOAD_SIZE = 100

private const val REQUEST_AMOUNT_EXCEEDED_CODE = 422

class UserRepositoriesPagingSource(
    private val repository: UserDetailsRepository,
    private val login: String,
) : PagingSource<Int, Repository>() {

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val page = params.key ?: STARTING_PAGE
        val loadSize = params.loadSize.coerceIn(MIN_LOAD_SIZE..MAX_LOAD_SIZE)
        val callResult = repository.getUserRepos(
            login = login,
            page = page,
            perPage = loadSize
        )

        val result = when (callResult) {
            is CallResult.Success -> {
                val data = callResult.value
                LoadResult.Page(data, null, calculateNextKey(data, page))
            }

            is CallResult.HttpError -> {
                if (callResult.code == REQUEST_AMOUNT_EXCEEDED_CODE) LoadResult.Invalid()
                else LoadResult.Error(callResult.throwable)
            }

            is CallResult.OtherError -> LoadResult.Error(callResult.throwable)
        }
        return result
    }

    private fun calculateNextKey(data: List<Repository>, currentPage: Int): Int? {
        val isMoreAvailable = data.size >= MAX_LOAD_SIZE
        return if (isMoreAvailable) currentPage + 1 else null
    }
}