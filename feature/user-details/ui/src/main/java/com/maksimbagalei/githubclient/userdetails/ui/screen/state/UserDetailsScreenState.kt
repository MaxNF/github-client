package com.maksimbagalei.githubclient.userdetails.ui.screen.state

import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel

internal sealed interface UserDetailsScreenState {
    data object Loading: UserDetailsScreenState
    data object Error: UserDetailsScreenState
    data class Loaded(val model: UserDetailsModel): UserDetailsScreenState
}