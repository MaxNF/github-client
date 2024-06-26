
package com.maksimbagalei.githubclient.userlist.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.designsystem.LocalDimens
import com.maksimbagalei.githubclient.designsystem.LocalScaffoldPaddingValues
import com.maksimbagalei.githubclient.designsystem.ThemePreviews
import com.maksimbagalei.githubclient.userlist.ui.model.UserBriefModel
import com.maksimbagalei.githubclient.userlist.ui.screen.state.UserListScreenState
import com.valentinilk.shimmer.shimmer

@Composable
internal fun BoxScope.UserList(
    searchingState: UserListScreenState.Searching,
    onUserDetailsClick: (String) -> Unit
) {
    val pagingItems = searchingState.pagingFlow.collectAsLazyPagingItems()

    when (pagingItems.loadState.source.refresh) {
        is LoadState.Loading -> InitialLoadShimmer()

        is LoadState.Error -> TryAgainPlaceholder { pagingItems.refresh() }

        is LoadState.NotLoading -> {
            if (pagingItems.itemCount == 0) {
                NoUserFoundPlaceholder(modifier = Modifier.align(Alignment.Center))
            } else {
                LoadedList(
                    userBriefs = pagingItems,
                    onUserDetailsClick = onUserDetailsClick
                )
            }
        }
    }
}

@Composable
private fun LoadedList(
    userBriefs: LazyPagingItems<UserBriefModel>,
    onUserDetailsClick: (String) -> Unit
) {
    val topPadding = LocalScaffoldPaddingValues.current.calculateTopPadding()
    val bottomPadding = LocalScaffoldPaddingValues.current.calculateBottomPadding()
    LazyColumn(
        contentPadding = PaddingValues(
            top = topPadding + LocalDimens.current.spacedByPadding,
            bottom = bottomPadding + LocalDimens.current.spacedByPadding
        ),
        verticalArrangement = Arrangement.spacedBy(LocalDimens.current.spacedByPadding)
    ) {
        showLoadedItems(
            userBriefs = userBriefs,
            onUserDetailsClick = onUserDetailsClick
        )
        handleAppendLoadingState(userBriefs = userBriefs)
    }
}

private fun LazyListScope.showLoadedItems(
    userBriefs: LazyPagingItems<UserBriefModel>,
    onUserDetailsClick: (String) -> Unit
) {
    items(count = userBriefs.itemCount, key = { userBriefs[it]?.id ?: it }) {
        val userBrief = userBriefs[it]
        userBrief?.let {
            UserListItem(item = userBrief, onUserDetailsClick)
        }
    }
}

private fun LazyListScope.handleAppendLoadingState(userBriefs: LazyPagingItems<UserBriefModel>) {
    when (userBriefs.loadState.source.append) {
        is LoadState.Loading -> {
            item {
                LoadingUserListItem()
            }
        }

        is LoadState.Error -> {
            singleItemLoadingError { userBriefs.retry() }
        }

        else -> {}
    }
}

private fun LazyListScope.singleItemLoadingError(onAppendReloadClick: () -> Unit) {
    item {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
        ) {
            val errorMsg = stringResource(id = R.string.user_brief_loading_error)
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
                Spacer(modifier = Modifier.size(LocalDimens.current.spacedByPadding))
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

@Composable
private fun InitialLoadShimmer() {
    val topBarPadding = LocalScaffoldPaddingValues.current.calculateTopPadding()
    val bottomBarPadding = LocalScaffoldPaddingValues.current.calculateBottomPadding()
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(
            top = topBarPadding + LocalDimens.current.spacedByPadding,
            bottom = bottomBarPadding + LocalDimens.current.spacedByPadding
        )
    ) {
        repeat(3) {
            LoadingUserListItem()
        }
    }
}

@Composable
private fun NoUserFoundPlaceholder(modifier: Modifier = Modifier) {
    val text = stringResource(id = R.string.no_user_found_placeholder)
    Text(modifier = modifier, text = text, style = MaterialTheme.typography.titleSmall)
}

@Composable
private fun BoxScope.TryAgainPlaceholder(
    onReloadClick: () -> Unit,
) {
    val errorMsg = stringResource(id = R.string.user_brief_loading_error)
    val tryAgainMsg = stringResource(id = R.string.try_again_button_label)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMsg, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.size(LocalDimens.current.spacedByPadding))
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

@Composable
private fun LoadingUserListItem() {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .shimmer()
            .fillMaxWidth()
            .height(96.dp)
    ) {}
}

@Composable
private fun UserListItem(item: UserBriefModel, onClick: (String) -> Unit) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick(item.login) },
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = LocalDimens.current.innerHorizontalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = LocalDimens.current.spacedByPadding)
                    .size(80.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = item.login,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalDimens.current.innerHorizontalPadding)
            )
        }
    }
}

@ThemePreviews
@Composable
private fun PreviewUserListItem() {
    AppTheme {
        UserListItem(item = UserBriefModel(1, "MaxNF", "")) {}
    }
}

@Preview
@Composable
private fun PreviewSingleItemLoadingError() {
    LazyColumn(content = {
        singleItemLoadingError { }
    })
}