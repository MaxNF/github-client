@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.maksimbagalei.githubclient.designsystem.ThemePreviews
import com.maksimbagalei.githubclient.userlist.ui.model.UserBriefModel
import com.valentinilk.shimmer.shimmer

@Composable
internal fun BoxScope.UserList(
    userBriefs: LazyPagingItems<UserBriefModel>,
    scrollBehavior: TopAppBarScrollBehavior,
    onUserDetailsClick: (String) -> Unit
) {
    when (userBriefs.loadState.source.refresh) {
        is LoadState.Loading -> {
            InitialLoadShimmer()
        }

        is LoadState.Error -> {
            TryAgainPlaceholder { userBriefs.refresh() }
        }

        is LoadState.NotLoading -> {
            if (userBriefs.itemCount == 0) {
                EmptyListPlaceholder(modifier = Modifier.align(Alignment.Center))
            } else {
                LoadedList(
                    scrollBehavior = scrollBehavior,
                    userBriefs = userBriefs,
                    onUserDetailsClick = onUserDetailsClick
                )
            }
        }
    }
}

@Composable
private fun LoadedList(
    scrollBehavior: TopAppBarScrollBehavior,
    userBriefs: LazyPagingItems<UserBriefModel>,
    onUserDetailsClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
            shape = RoundedCornerShape(16.dp),
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

@Composable
private fun InitialLoadShimmer() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        repeat(3) {
            LoadingUserListItem()
        }
    }
}

@Composable
private fun EmptyListPlaceholder(modifier: Modifier = Modifier) {
    val text = stringResource(id = R.string.empty_list_placeholder)
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

@Composable
private fun LoadingUserListItem() {
    Surface(
        shape = RoundedCornerShape(16.dp),
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
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick(item.login) },
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(80.dp)
                    .clip(shape = CircleShape)
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = item.login,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
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