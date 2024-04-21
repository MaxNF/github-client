package com.maksimbagalei.githubclient.userdetails.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetails(
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
    val name: String? = null,
    val followers: Int,
    val following: Int,
)
