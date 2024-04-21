package com.maksimbagalei.githubclient.userdetails.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val name: String,
    val language: String,
    @SerialName("stargazers_count")
    val stargazers: Int,
    val description: String,
    @SerialName("html_url")
    val url: String,
    @SerialName("fork")
    val isFork: Boolean
)