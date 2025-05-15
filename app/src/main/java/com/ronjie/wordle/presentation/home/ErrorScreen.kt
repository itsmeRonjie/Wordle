package com.ronjie.wordle.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
internal fun BoxScope.ErrorScreen(
    state: GameState,
    shownError: () -> Unit
) {
    LaunchedEffect(state.doesNotExist) {
        if (state.doesNotExist) {
            delay(2000)
            shownError()
        }
    }

    AnimatedVisibility(
        visible = state.doesNotExist,
        modifier = Modifier.align(Alignment.BottomCenter)
    ) {
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.error)
                .padding(16.dp)
        ) {
            Text(
                text = "The word does not exist!",
                color = MaterialTheme.colorScheme.onError
            )
        }
    }
}
