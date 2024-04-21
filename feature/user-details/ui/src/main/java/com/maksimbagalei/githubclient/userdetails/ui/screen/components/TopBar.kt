@file:OptIn(ExperimentalMaterial3Api::class)

package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.maksimbagalei.githubclient.R

@Composable
internal fun TopBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    TopAppBar(
        navigationIcon = { NavigationIcon(onBackClick) },
        title = { TopBarTitle() }
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
    TopBar {

    }
}