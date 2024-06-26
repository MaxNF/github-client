@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userlist.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.LocalScaffoldPaddingValues
import com.maksimbagalei.githubclient.designsystem.ThemePreviews
import com.maksimbagalei.githubclient.userlist.ui.screen.components.UserList
import com.maksimbagalei.githubclient.userlist.ui.screen.components.UserListTopBar
import com.maksimbagalei.githubclient.userlist.ui.screen.state.UserListScreenState
import com.maksimbagalei.githubclient.userlist.ui.viewmodel.UserListViewModel

@Composable
fun UserListScreen(
    modifier: Modifier = Modifier,
    onDetailsClick: (String) -> Unit,
) {
    val viewModel: UserListViewModel = hiltViewModel()
    val state = viewModel.users.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    ScreenContent(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        state = state,
        onDetailsClick = onDetailsClick,
        onUserSearch = viewModel::searchUsers
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    state: State<UserListScreenState>,
    onDetailsClick: (String) -> Unit,
    onUserSearch: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
        UserListTopBar(
            scrollBehavior = scrollBehavior,
            onUserSearch = onUserSearch
        )
    }) { paddingValues ->
        CompositionLocalProvider(LocalScaffoldPaddingValues provides paddingValues) {
            val layoutDirection = LocalLayoutDirection.current
            val startPadding = paddingValues.calculateStartPadding(layoutDirection)
            val endPadding = paddingValues.calculateEndPadding(layoutDirection)
            Box(modifier = modifier
                .fillMaxSize()
                .padding(start = startPadding, end = endPadding)) {
                when (val stateValue = state.value) {
                    UserListScreenState.Empty -> {
                        TryToSearchSomethingPlaceholder(
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }

                    is UserListScreenState.Searching -> {
                        UserList(
                            searchingState = stateValue,
                            onUserDetailsClick = onDetailsClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TryToSearchSomethingPlaceholder(modifier: Modifier = Modifier) {
    val text = stringResource(id = R.string.try_to_search_something)
    Text(modifier = modifier, text = text, style = MaterialTheme.typography.titleSmall)
}

@ThemePreviews
@Composable
private fun UserListScreenPreview() {
    UserListScreen(onDetailsClick = {})
}
