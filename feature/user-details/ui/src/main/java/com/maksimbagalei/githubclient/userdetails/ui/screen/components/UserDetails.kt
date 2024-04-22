package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.userdetails.ui.model.RepositoryModel
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel
import com.maksimbagalei.githubclient.userdetails.ui.screenstate.UserDetailsScreenState
import com.valentinilk.shimmer.shimmer

@Composable
internal fun UserDetails(
    state: UserDetailsScreenState,
    pagingData: LazyPagingItems<RepositoryModel>,
    onDetailsReloadClick: () -> Unit,
    onRepoClick: (String) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Column {
                when (state) {
                    UserDetailsScreenState.Loading -> {
                        UserDetailsShimmer()
                    }

                    UserDetailsScreenState.Error -> {
                        TryAgain(onDetailsReloadClick)
                    }

                    is UserDetailsScreenState.Loaded -> {
                        LoadedState(state.model)
                    }
                }
                Text(
                    text = stringResource(id = R.string.repos_label),
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        when (pagingData.loadState.source.refresh) {
            is LoadState.Loading -> {
                repeat(3) {
                    item {
                        LoadingRepoItem()
                    }
                }
            }

            is LoadState.Error -> {
                item {
                    TryAgainPlaceholder(pagingData::refresh)
                }
            }

            is LoadState.NotLoading -> {
                if (pagingData.itemCount == 0) {
                    item {
                        EmptyListPlaceholder()
                    }
                } else {
                    items(pagingData.itemCount, key = { pagingData[it]?.id ?: it }) {
                        pagingData[it]?.let { model ->
                            RepositoryItem(item = model, onClick = onRepoClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TryAgainPlaceholder(
    onReloadClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val errorMsg = stringResource(id = R.string.user_brief_loading_error)
        val tryAgainMsg = stringResource(id = R.string.try_again_button_label)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorMsg, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                modifier = Modifier.clickable {
                    onReloadClick()
                },
                text = tryAgainMsg,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun LoadingRepoItem() {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .height(64.dp)
    ) {}
}

@Composable
private fun EmptyListPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val text = stringResource(id = R.string.no_repos_placeholder)
        Text(modifier = modifier, text = text, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun LoadedState(model: UserDetailsModel) {
    AvatarSection(model = model)
    Spacer(modifier = Modifier.height(height = 16.dp))
}

@Composable
private fun RepositoryItem(item: RepositoryModel, onClick: (String) -> Unit) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(item.url) },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                )
                Text(
                    text = item.language,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.description,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(modifier = Modifier) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
                Text(
                    text = item.stargazers,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private fun LazyListScope.handleAppendLoadingState(pagingData: LazyPagingItems<RepositoryModel>) {
    when (pagingData.loadState.source.append) {
        is LoadState.Loading -> {
            item {
                LoadingRepoItem()
            }
        }

        is LoadState.Error -> {
            singleItemLoadingError { pagingData.retry() }
        }

        else -> {}
    }
}

private fun LazyListScope.singleItemLoadingError(onAppendReloadClick: () -> Unit) {
    item {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            val errorMsg = stringResource(id = R.string.repo_loading_error)
            val tryAgainMsg = stringResource(id = R.string.try_again_button_label)
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = errorMsg,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    modifier = Modifier.clickable {
                        onAppendReloadClick()
                    },
                    text = tryAgainMsg,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun RepositoryItemPreview() {
    AppTheme {
        RepositoryItem(item = RepositoryModel(0, "name", "language", "5", "description", "")) {

        }
    }
}