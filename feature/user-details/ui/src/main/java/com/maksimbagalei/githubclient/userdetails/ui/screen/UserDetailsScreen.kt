package com.maksimbagalei.githubclient.userdetails.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.userdetails.ui.model.RepositoryModel
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.UserDetails
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.TopBar
import com.maksimbagalei.githubclient.userdetails.ui.screenstate.UserDetailsScreenState
import com.maksimbagalei.githubclient.userdetails.ui.viewmodel.UserDetailsViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun UserDetailsScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    val viewModel: UserDetailsViewModel = hiltViewModel()
    val state = viewModel.screenState.collectAsState()
    val pagingData = viewModel.repositories.collectAsLazyPagingItems()
    ScreenContent(
        modifier = modifier.padding(16.dp),
        onBackClick = onBackClick,
        onReloadClick = viewModel::fetchDetails,
        state = state,
        pagingData = pagingData,
        onRepoClick = {} //todo
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onReloadClick: () -> Unit,
    onBackClick: () -> Unit,
    state: State<UserDetailsScreenState>,
    pagingData: LazyPagingItems<RepositoryModel>,
    onRepoClick: (String) -> Unit
) {
    Column(modifier = modifier) {
        TopBar(onBackClick = onBackClick)
        UserDetails(state.value, pagingData, onReloadClick, onRepoClick)
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
private fun LoadedStatePreview() {
    val state = UserDetailsScreenState.Loaded(
        UserDetailsModel(
            "login",
            "avatarUrl",
            "name",
            "company",
            "blogUrl",
        )
    )

    val repo = RepositoryModel(0, "name", "language", "5", "description", "")
    AppTheme {
        Box(Modifier.padding(16.dp)) {
            UserDetails(
                state = state,
                pagingData = flowOf(PagingData.from(listOf(repo))).collectAsLazyPagingItems(),
                onDetailsReloadClick = {},
                onRepoClick = {}
            )
        }
    }
}