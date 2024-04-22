@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userlist.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.AppTheme

private const val MAX_SEARCH_LENGTH = 256

@Composable
fun UserListTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onUserSearch: (String) -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            TopBarTitle(onUserSearch)
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun TopBarTitle(onUserSearch: (String) -> Unit) {
    var search by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = search,
        onValueChange = {
            if (it.length <= MAX_SEARCH_LENGTH) {
                search = it
                onUserSearch(it)
            }
        },
        placeholder = { SearchPlaceholder() },
        singleLine = true
    )
}

@Composable
private fun SearchPlaceholder() {
    val placeholderText = stringResource(id = R.string.empty_search_placeholder)
    Text(placeholderText, style = MaterialTheme.typography.bodyLarge)
}

@Preview
@Composable
private fun PreviewTopBar() {
    AppTheme {
        UserListTopBar(
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            onUserSearch = {}
        )
    }
}