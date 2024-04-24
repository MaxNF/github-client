package com.maksimbagalei.githubclient.userlist.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBrief(
    val id: Long,
    val login: String,
    @SerialName("avatar_url")
    val avatarUrl: String
)