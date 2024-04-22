@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userlist.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.maksimbagalei.githubclient.designsystem.ThemePreviews
import com.maksimbagalei.githubclient.userlist.ui.model.UserBriefModel
import com.maksimbagalei.githubclient.userlist.ui.screen.components.UserList
import com.maksimbagalei.githubclient.userlist.ui.screen.components.UserListTopBar
import com.maksimbagalei.githubclient.userlist.ui.viewmodel.UserListViewModel

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    onDetailsClick: (String) -> Unit,
) {
    val viewModel: UserListViewModel = hiltViewModel()
    val items = viewModel.users.collectAsLazyPagingItems()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    ScreenContent(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        items = items,
        onDetailsClick = onDetailsClick,
        onUserSearch = viewModel::searchUsers
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    items: LazyPagingItems<UserBriefModel>,
    onDetailsClick: (String) -> Unit,
    onUserSearch: (String) -> Unit,
) {
    Scaffold(topBar = {
        UserListTopBar(
            scrollBehavior = scrollBehavior,
            onUserSearch = onUserSearch
        )
    }) {
        Column(modifier = modifier.padding(it)) {
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
}

@ThemePreviews
@Composable
private fun UserListScreenPreview() {
    UserListScreen(onDetailsClick = {})
}
