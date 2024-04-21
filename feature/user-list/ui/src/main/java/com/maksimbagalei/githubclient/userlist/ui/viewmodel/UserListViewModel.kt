@file:OptIn(ExperimentalCoroutinesApi::class)

package com.maksimbagalei.githubclient.userlist.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.domain.SearchUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val LAST_SEARCH_KEY = "last_search"
private const val DELAY_BEFORE_REQUEST = 1_000L // 1 sec

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val state: SavedStateHandle
) : ViewModel() {
    private val search = MutableStateFlow("")

    val users = search.flatMapLatest {
        if (it.isNotBlank()) {
            delay(DELAY_BEFORE_REQUEST)
            state[LAST_SEARCH_KEY] = it
            searchUsersUseCase.invoke(it)
        } else {
            flowOf(
                PagingData.from(
                    emptyList<UserBrief>(),
                    sourceLoadStates = LoadStates(
                        LoadState.NotLoading(true),
                        LoadState.NotLoading(true),
                        LoadState.NotLoading(true)
                    )
                )
            )
        }
    }

    init {
        val savedValue = state.get<String>(LAST_SEARCH_KEY)
        savedValue?.let {
            searchUsers(it)
        }
    }

    fun searchUsers(name: String) {
        viewModelScope.launch {
            search.emit(name)
        }
    }
}