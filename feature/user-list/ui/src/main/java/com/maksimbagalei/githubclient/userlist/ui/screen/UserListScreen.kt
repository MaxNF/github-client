@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userlist.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.maksimbagalei.githubclient.designsystem.ThemePreviews
import com.maksimbagalei.githubclient.userlist.data.dto.UserBrief
import com.maksimbagalei.githubclient.userlist.ui.screen.components.TopBar
import com.maksimbagalei.githubclient.userlist.ui.screen.components.UserList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    list: Flow<PagingData<UserBrief>>,
    onDetailsClick: (String) -> Unit,
    onUserSearch: (String) -> Unit,
) {
    val items = list.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Column(modifier = modifier) {
        TopBar(scrollBehavior = scrollBehavior, onUserSearch = onUserSearch)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            UserList(
                userBriefs = items,
                scrollBehavior = scrollBehavior,
                onUserDetailsClick = onDetailsClick
            )
        }
    }
}

@ThemePreviews
@Composable
fun UserListScreenPreview() {
    UserListScreen(list = flowOf(), onDetailsClick = {}, onUserSearch = {})
}
