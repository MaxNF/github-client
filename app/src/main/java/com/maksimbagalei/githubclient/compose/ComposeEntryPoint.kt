package com.maksimbagalei.githubclient.compose

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.navigation.Destination
import com.maksimbagalei.githubclient.navigation.userDetailsScreen
import com.maksimbagalei.githubclient.navigation.userListScreen

@Composable
fun ComposeEntryPoint() {
    val navController = rememberNavController()
    AppTheme {
        Scaffold {
            NavHost(
                modifier = Modifier
                    .padding(it)
                    .consumeWindowInsets(it),
                navController = navController,
                startDestination = Destination.UserList.route
            ) {
                userListScreen(
                    onDetailsClick = { login ->
                        navController.navigate(
                            Destination.UserDetails.createRoute(login)
                        )
                    }
                )
                userDetailsScreen(navController::popBackStack)
            }
        }
    }
}