package com.maksimbagalei.githubclient.common.util

import androidx.annotation.FloatRange

fun calculateAppBarAlpha(
    @FloatRange(from = 0.0, to = 1.0) startingAlpha: Float,
    @FloatRange(from = 0.0, to = 1.0) finalAlpha: Float,
    @FloatRange(from = 0.0, to = 1.0) collapsedFraction: Float
): Float {
    val range = (startingAlpha - finalAlpha)
    return (finalAlpha + range * (1 - collapsedFraction)).coerceIn(0f, 1f)
}