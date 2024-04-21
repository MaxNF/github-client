package com.maksimbagalei.githubclient.navigation

internal sealed class Destination(val route: String) {
    data object UserList: Destination("user_list")
    data object UserDetails: Destination("user_details/{login}") {
        fun createRoute(login: String) = "user_details/$login"
    }
}