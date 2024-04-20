package com.maksimbagalei.githubclient.designsystem

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import androidx.compose.ui.tooling.preview.Preview

@Preview(uiMode = UI_MODE_NIGHT_NO, showBackground = true, name = "Light theme", apiLevel = Build.VERSION_CODES.R, widthDp = 500, heightDp = 1000)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, name = "Dark theme", apiLevel = Build.VERSION_CODES.R, widthDp = 500, heightDp = 1000)
annotation class ThemePreviews