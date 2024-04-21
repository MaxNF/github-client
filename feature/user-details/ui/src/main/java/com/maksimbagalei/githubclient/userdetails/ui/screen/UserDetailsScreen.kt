package com.maksimbagalei.githubclient.userdetails.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.AvatarSection
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.TopBar
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.TryAgain
import com.maksimbagalei.githubclient.userdetails.ui.screen.components.UserDetailsShimmer
import com.maksimbagalei.githubclient.userdetails.ui.screenstate.UserDetailsScreenState
import com.maksimbagalei.githubclient.userdetails.ui.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    val viewModel: UserDetailsViewModel = hiltViewModel()
    val state = viewModel.screenState.collectAsState()
    ScreenContent(
        modifier = Modifier.padding(16.dp),
        onBackClick = onBackClick,
        onReloadClick = viewModel::fetchDetails,
        state = state
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onReloadClick: () -> Unit,
    onBackClick: () -> Unit,
    state: State<UserDetailsScreenState>
) {
    Column {
        TopBar(onBackClick = onBackClick)
        Box(modifier = modifier) {
            when (val stateValue = state.value) {
                UserDetailsScreenState.Loading -> {
                    UserDetailsShimmer()
                }

                UserDetailsScreenState.Error -> {
                    TryAgain(onReloadClick)
                }

                is UserDetailsScreenState.Loaded -> {
                    LoadedState(stateValue.model)
                }
            }
        }
    }
}

@Composable
private fun BoxScope.LoadedState(model: UserDetailsModel) {
    Column {
        AvatarSection(model = model)
        Spacer(modifier = Modifier.height(height = 16.dp))
//        RepositoriesSection(model)
    }
}

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
private fun LoadedStatePreview() {
    Box(Modifier.padding(16.dp)) {
        LoadedState(
            model = UserDetailsModel(
                "login",
                "avatarUrl",
                "name",
                "company",
                "blogUrl",
            )
        )
    }
}