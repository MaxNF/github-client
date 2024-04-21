package com.maksimbagalei.githubclient.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maksimbagalei.githubclient.userdetails.ui.screen.UserDetailsScreen
import com.maksimbagalei.githubclient.userlist.ui.screen.UserListScreen

fun NavGraphBuilder.userListScreen(onDetailsClick: (String) -> Unit) {
    composable(Destination.UserList.route) {
        UserListScreen(onDetailsClick = onDetailsClick)
    }
}

fun NavGraphBuilder.userDetailsScreen(onBackClick: () -> Unit) {
    composable(route = Destination.UserDetails.route) {
        UserDetailsScreen(onBackClick = onBackClick)
    }
}