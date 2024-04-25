package com.maksimbagalei.githubclient.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.designsystem.LocalDimens
import com.maksimbagalei.githubclient.navigation.Destination
import com.maksimbagalei.githubclient.navigation.userDetailsScreen
import com.maksimbagalei.githubclient.navigation.userListScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ComposeEntryPoint() {
    val navController = rememberNavController()
    AppTheme {
        val globalPaddings = LocalDimens.current.globalHorizontalPadding
        Scaffold {
            NavHost(
                navController = navController,
                startDestination = Destination.UserList.route
            ) {
                userListScreen(
                    modifier = Modifier.padding(horizontal = globalPaddings),
                    onDetailsClick = { login ->
                        navController.navigate(
                            Destination.UserDetails.createRoute(login)
                        )
                    }
                )
                userDetailsScreen(
                    modifier = Modifier.padding(horizontal = globalPaddings),
                    navController::popBackStack
                )
            }
        }
    }
}