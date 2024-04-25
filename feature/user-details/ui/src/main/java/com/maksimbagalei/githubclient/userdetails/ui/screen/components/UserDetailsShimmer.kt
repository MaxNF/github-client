package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maksimbagalei.githubclient.designsystem.AppTheme
import com.valentinilk.shimmer.shimmer

@Composable
internal fun UserDetailsShimmer(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .fillMaxWidth()
        .shimmer()) {
        Surface(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {}
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .height(100.dp)
        ) {}
    }
}

@Preview
@Composable
private fun UserDetailsShimmerPreview() {
    AppTheme {
        UserDetailsShimmer()
    }
}