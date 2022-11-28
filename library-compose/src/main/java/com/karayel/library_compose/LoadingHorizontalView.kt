package com.karayel.library_compose

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.karayel.library_compose.HorizontalProgressState.INFINITIVE

@Composable
fun LoadingHorizontalView(
    modifier: Modifier = Modifier,
    progressState: HorizontalProgressState = INFINITIVE,
    @FloatRange(0.0,1.0) progressValue: Float = 0.0F
) {

    val progress by remember { mutableStateOf(progressValue) }
    val progressModifier: Modifier = modifier.fillMaxWidth()

    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    if (progressState == INFINITIVE) {
        LinearProgressIndicator(modifier = progressModifier)
    } else {
        LinearProgressIndicator(modifier = progressModifier, progress = animatedProgress)
    }
}

enum class HorizontalProgressState {
    INFINITIVE, PROGRESSIVE
}
