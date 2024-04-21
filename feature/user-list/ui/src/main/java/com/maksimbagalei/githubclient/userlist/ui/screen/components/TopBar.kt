package com.maksimbagalei.githubclient.userlist.ui.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maksimbagalei.githubclient.R

private const val MAX_SEARCH_LENGTH = 256

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier, onUserSearch: (String) -> Unit) {
    TopAppBar(
        modifier = modifier,
        title = {
            TopBarTitle(onUserSearch)
        })
}

@Composable
fun TopBarTitle(onUserSearch: (String) -> Unit) {
    var search by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = search,
        onValueChange = {
            if (it.length <= MAX_SEARCH_LENGTH) {
                search = it
                onUserSearch(it)
            }
        },
        label = { SearchPlaceholder() },
        singleLine = true
    )
}

@Composable
fun SearchPlaceholder() {
    val placeholderText = stringResource(id = R.string.empty_search_placeholder)
    Text(placeholderText, style = MaterialTheme.typography.bodyLarge)
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar {

    }
}