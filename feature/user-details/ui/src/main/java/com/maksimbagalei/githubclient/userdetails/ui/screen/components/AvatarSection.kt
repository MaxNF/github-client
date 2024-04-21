package com.maksimbagalei.githubclient.userdetails.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.maksimbagalei.githubclient.R
import com.maksimbagalei.githubclient.userdetails.ui.model.UserDetailsModel

@Composable
internal fun AvatarSection(model: UserDetailsModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .semantics {
                    this.testTag = model.avatarUrl
                },
            model = model.avatarUrl,
            contentDescription = "avatar",
        )
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = model.login,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    text = model.name ?: "",
                    style = MaterialTheme.typography.headlineSmall
                )
                val followingLabel =
                    stringResource(id = R.string.following_label)
                Text(
                    text = "$followingLabel ${model.following}",
                    style = MaterialTheme.typography.bodyMedium
                )
                val followersLabel =
                    stringResource(id = R.string.followers_label)
                Text(
                    text = "$followersLabel ${model.followers}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
