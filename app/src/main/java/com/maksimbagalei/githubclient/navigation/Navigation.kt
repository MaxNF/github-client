package com.maksimbagalei.githubclient.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maksimbagalei.githubclient.userlist.ui.screen.UserListScreen

fun NavGraphBuilder.userListScreen(onDetailsClick: (String) -> Unit) {
    composable(Destination.UserList.route) {
        UserListScreen(onDetailsClick = onDetailsClick)
    }
}