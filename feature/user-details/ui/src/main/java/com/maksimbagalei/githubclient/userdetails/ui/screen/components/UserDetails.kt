
package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.LocalDimens
import com.maksimbagalei.githubclient.designsystem.LocalScaffoldPaddingValues
import com.maksimbagalei.githubclient.userdetails.ui.screen.state.UserDetailsScreenState

@Composable
internal fun UserDetails(
    modifier: Modifier = Modifier,
    state: State<UserDetailsScreenState>,
    onDetailsReloadClick: () -> Unit,
    onRepoClick: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when (val screenState = state.value) {
            UserDetailsScreenState.Loading -> {
                UserDetailsShimmer(modifier = modifier.padding(LocalScaffoldPaddingValues.current))
            }

            UserDetailsScreenState.Error -> {
                TryAgain(
                    modifier = modifier
                        .align(Alignment.Center)
                        .padding(LocalScaffoldPaddingValues.current),
                    onReloadClick = onDetailsReloadClick
                )
            }

            is UserDetailsScreenState.Loaded -> {
                LoadedState(
                    loadedState = screenState,
                    onRepoClick = onRepoClick
                )
            }
        }
    }
}

@Composable
private fun LoadedState(
    modifier: Modifier = Modifier,
    loadedState: UserDetailsScreenState.Loaded,
    onRepoClick: (String) -> Unit,
) {
    val topBarPadding = LocalScaffoldPaddingValues.current.calculateTopPadding()
    val bottomBarPadding = LocalScaffoldPaddingValues.current.calculateBottomPadding()
    val pagingData = loadedState.pagingData.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = topBarPadding + LocalDimens.current.spacedByPadding,
            bottom = bottomBarPadding + LocalDimens.current.spacedByPadding
        ),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.spacedByPadding)
    ) {
        item {
            Column {
                UserDetailsSection(model = loadedState.model)
                Spacer(modifier = Modifier.height(height = 16.dp))
                Text(
                    text = stringResource(id = R.string.repos_label),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        RepositorySection(pagingData = pagingData, onRepoClick = onRepoClick)
    }
}
