@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.AppTheme

@Composable
internal fun UserDetailsTopBar(scrollBehavior: TopAppBarScrollBehavior, onBackClick: () -> Unit) {
    val alpha = (1.5f - scrollBehavior.state.collapsedFraction).coerceIn(0f, 1f)
    val containerColor = TopAppBarDefaults.topAppBarColors().containerColor

    TopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = { NavigationIcon(onBackClick) },
        title = { TopBarTitle() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor.copy(alpha),
            scrolledContainerColor = containerColor.copy(alpha)
        )
    )
}

@Composable
private fun NavigationIcon(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
private fun TopBarTitle() {
    val title = stringResource(id = R.string.user_details_title)
    Text(text = title)
}

@Preview
@Composable
private fun PreviewTopBar() {
    AppTheme {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        UserDetailsTopBar(scrollBehavior = scrollBehavior) {

        }
    }
}