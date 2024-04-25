package com.maksimbagalei.githubclient.designsystem

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import javax.annotation.concurrent.Immutable

@Immutable
data class Dimens(
    val globalHorizontalPadding: Dp,
    val spacedByPadding: Dp,
    val sidePadding: Dp,
    val sectionPadding: Dp,
    val innerVerticalPadding: Dp,
    val innerHorizontalPadding: Dp
)

val LocalDimens = staticCompositionLocalOf {
    Dimens(
        globalHorizontalPadding = 0.dp,
        spacedByPadding = 0.dp,
        sidePadding = 0.dp,
        sectionPadding = 0.dp,
        innerVerticalPadding = 0.dp,
        innerHorizontalPadding = 0.dp
    )
}