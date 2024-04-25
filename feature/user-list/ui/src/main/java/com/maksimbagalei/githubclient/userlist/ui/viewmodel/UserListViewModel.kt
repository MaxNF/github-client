@file:OptIn(ExperimentalCoroutinesApi::class)

package com.maksimbagalei.githubclient.userlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.maksimbagalei.githubclient.userlist.domain.SearchUsersUseCase
import com.maksimbagalei.githubclient.userlist.ui.mapper.UserBriefToUserBriefModelMapper
import com.maksimbagalei.githubclient.userlist.ui.screen.state.UserListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_BEFORE_REQUEST = 1_000L // 1 sec

@HiltViewModel
internal class UserListViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val userBriefModelMapper: UserBriefToUserBriefModelMapper
) : ViewModel() {
    private val search = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val users = search.mapLatest { searchString ->
        if (searchString.isNotBlank()) {
            delay(DELAY_BEFORE_REQUEST)
            UserListScreenState.Searching(searchUsersUseCase.invoke(searchString)
                .map { pagingData ->
                    pagingData.map(userBriefModelMapper::map)
                }.cachedIn(viewModelScope)
            )
        } else UserListScreenState.Empty
    }.stateIn(viewModelScope, SharingStarted.Lazily, UserListScreenState.Empty)

    fun searchUsers(name: String) {
        viewModelScope.launch {
            search.emit(name)
        }
    }
}
