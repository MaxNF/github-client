package com.maksimbagalei.githubclient.userlist.ui.screen.state

import androidx.paging.PagingData
import com.maksimbagalei.githubclient.userlist.ui.model.UserBriefModel
import kotlinx.coroutines.flow.Flow

internal sealed interface UserListScreenState {
    data object Empty: UserListScreenState
    data class Searching(
        val pagingFlow: Flow<PagingData<UserBriefModel>>
    ): UserListScreenState
}