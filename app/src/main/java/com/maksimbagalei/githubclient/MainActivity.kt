package com.maksimbagalei.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.navigation.Destination
import com.maksimbagalei.githubclient.navigation.userDetailsScreen
import com.maksimbagalei.githubclient.navigation.userListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Scaffold(
                ) {
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
    }
}