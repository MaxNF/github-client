package com.maksimbagalei.githubclient.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.maksimbagalei.githubclient.userdetails.ui.screen.UserDetailsScreen
import com.maksimbagalei.githubclient.userlist.ui.screen.UserListScreen

fun NavGraphBuilder.userListScreen(modifier: Modifier = Modifier, onDetailsClick: (String) -> Unit) {
    composable(Destination.UserList.route) {
        UserListScreen(modifier = modifier, onDetailsClick = onDetailsClick)
    }
}

fun NavGraphBuilder.userDetailsScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    composable(route = Destination.UserDetails.route) {
        UserDetailsScreen(modifier = modifier, onBackClick = onBackClick)
    }
}