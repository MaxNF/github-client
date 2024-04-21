package com.maksimbagalei.githubclient.userlist.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserList(val items: List<UserBrief>)