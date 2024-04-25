package com.maksimbagalei.githubclient.userdetails.ui.screen.state

import androidx.paging.PagingData
import com.maksimbagalei.githubclient.userdetails.ui.model.RepositoryModel
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel
import kotlinx.coroutines.flow.Flow

internal sealed interface UserDetailsScreenState {
    data object Loading : UserDetailsScreenState
    data object Error : UserDetailsScreenState
    data class Loaded(
        val model: UserDetailsModel,
        val pagingData: Flow<PagingData<RepositoryModel>>
    ) :
        UserDetailsScreenState
}