@file:OptIn(ExperimentalCoroutinesApi::class)

package com.maksimbagalei.githubclient.userlist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.maksimbagalei.githubclient.common.util.createNotLoadingPagingData
import com.maksimbagalei.githubclient.userlist.domain.SearchUsersUseCase
import com.maksimbagalei.githubclient.userlist.ui.mapper.UserBriefToUserBriefModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DELAY_BEFORE_REQUEST = 1_000L // 1 sec

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val userBriefModelMapper: UserBriefToUserBriefModelMapper
) : ViewModel() {
    private val search = MutableStateFlow("")

    val users = search.flatMapLatest { searchString ->
        if (searchString.isNotBlank()) {
            delay(DELAY_BEFORE_REQUEST)
            searchUsersUseCase.invoke(searchString)
                .map { pagingData ->
                    pagingData.map(userBriefModelMapper::map)
                }
        } else flowOf(createNotLoadingPagingData())
    }

    fun searchUsers(name: String) {
        viewModelScope.launch {
            search.emit(name)
        }
    }
}
