package com.maksimbagalei.githubclient.common.util

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
fun <T: Any> createNotLoadingPagingData() = PagingData.from(
    emptyList<T>(),
    sourceLoadStates = LoadStates(
        LoadState.NotLoading(true),
        LoadState.NotLoading(true),
        LoadState.NotLoading(true)
    )
)