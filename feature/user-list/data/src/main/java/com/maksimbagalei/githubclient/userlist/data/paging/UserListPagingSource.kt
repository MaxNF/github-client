package com.maksimbagalei.githubclient.userlist.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.maksimbagalei.githubclient.repo.CallResult
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.data.repo.UserListRepository

private const val STARTING_PAGE = 1
private const val MIN_LOAD_SIZE = 1
private const val MAX_LOAD_SIZE = 100

private const val REQUEST_AMOUNT_EXCEEDED_CODE = 422

class UserListPagingSource(
    private val repository: UserListRepository,
    private val searchName: String,
) : PagingSource<Int, UserBrief>() {

    override fun getRefreshKey(state: PagingState<Int, UserBrief>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserBrief> {
        val page = params.key ?: STARTING_PAGE
        val loadSize = params.loadSize.coerceIn(MIN_LOAD_SIZE..MAX_LOAD_SIZE)
        val callResult = repository.searchUsers(
            name = searchName,
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

    private fun calculateNextKey(data: List<UserBrief>, currentPage: Int): Int? {
        val isMoreAvailable = data.size >= MAX_LOAD_SIZE
        return if (isMoreAvailable) currentPage + 1 else null
    }
}