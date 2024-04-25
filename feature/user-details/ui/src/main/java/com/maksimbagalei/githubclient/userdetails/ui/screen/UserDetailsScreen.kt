@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userdetails.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.designsystem.LocalScaffoldPaddingValues
import com.maksimbagalei.githubclient.userdetails.ui.model.RepositoryModel
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.UserDetails
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.UserDetailsTopBar
import com.maksimbagalei.githubclient.userdetails.ui.screen.state.UserDetailsScreenState
import com.maksimbagalei.githubclient.userdetails.ui.viewmodel.UserDetailsViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserDetailsScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    val viewModel: UserDetailsViewModel = hiltViewModel()
    val state = viewModel.screenState.collectAsState()
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    ScreenContent(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        onBackClick = onBackClick,
        onReloadClick = viewModel::reload,
        state = state,
        onRepoClick = { viewModel.openRepo(context, it) }
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onReloadClick: () -> Unit,
    onBackClick: () -> Unit,
    state: State<UserDetailsScreenState>,
    onRepoClick: (String) -> Unit
) {
    Scaffold(topBar = { UserDetailsTopBar(scrollBehavior, onBackClick) }) { paddingValues ->
        CompositionLocalProvider(LocalScaffoldPaddingValues provides paddingValues) {
            UserDetails(
                modifier,
                scrollBehavior,
                state,
                onReloadClick,
                onRepoClick
            )
        }
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
private fun LoadedStateLoadedPreview() {
    val repo = RepositoryModel(0, "name", "language", "5", "description", "")
    val state = UserDetailsScreenState.Loaded(
        UserDetailsModel(
            "login",
            "avatarUrl",
            "name",
            "company",
            "blogUrl",
        ),
        flowOf(PagingData.from(listOf(repo)))
    )


    AppTheme {
        Box(Modifier.padding(16.dp)) {
            UserDetails(
                state = remember { mutableStateOf(state) },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                onDetailsReloadClick = {},
                onRepoClick = {}
            )
        }
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
private fun LoadedStateErrorPreview() {
    val state = UserDetailsScreenState.Error

    AppTheme {
        Box(Modifier.padding(16.dp)) {
            UserDetails(
                state = remember { mutableStateOf(state) },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                onDetailsReloadClick = {},
                onRepoClick = {}
            )
        }
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
private fun LoadedStateLoadingPreview() {
    val state = UserDetailsScreenState.Loading

    AppTheme {
        Box(Modifier.padding(16.dp)) {
            UserDetails(
                state = remember { mutableStateOf(state) },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                onDetailsReloadClick = {},
                onRepoClick = {}
            )
        }
    }
}