package com.maksimbagalei.githubclient.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maksimbagalei.githubclient.userlist.ui.screen.UserListScreen
import com.maksimbagalei.githubclient.userlist.ui.viewmodel.UserListViewModel

fun NavGraphBuilder.userListScreen(onDetailsClick: (String) -> Unit) {
    composable(Destination.UserList.route) {
        val viewModel: UserListViewModel = hiltViewModel()
        UserListScreen(
            list = viewModel.users,
            onDetailsClick = onDetailsClick,
            onUserSearch = viewModel::searchUsers,
        )
    }
}