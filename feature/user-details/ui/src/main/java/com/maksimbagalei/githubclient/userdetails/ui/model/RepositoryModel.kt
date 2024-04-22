package com.maksimbagalei.githubclient.userdetails.ui.model

internal data class RepositoryModel(
    val id: Long,
    val name: String,
    val language: String?,
    val stargazers: String,
    val description: String?,
    val url: String,
)