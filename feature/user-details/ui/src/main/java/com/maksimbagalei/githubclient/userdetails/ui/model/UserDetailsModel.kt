package com.maksimbagalei.githubclient.userdetails.ui.model

data class UserDetailsModel(
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val followers: String,
    val following: String
)