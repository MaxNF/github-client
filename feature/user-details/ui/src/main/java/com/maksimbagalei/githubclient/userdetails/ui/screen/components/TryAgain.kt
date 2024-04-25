package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.designsystem.AppTheme

@Composable
internal fun TryAgain(modifier: Modifier = Modifier, onReloadClick: () -> Unit) {
    val errorMsg = stringResource(id = R.string.user_brief_loading_error)
    val tryAgainMsg = stringResource(id = R.string.try_again_button_label)
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMsg, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(8.dp))
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

@Preview(widthDp = 400, heightDp = 700, showBackground = true)
@Composable
private fun TryAgainPreview() {
    AppTheme {
        TryAgain {}
    }
}