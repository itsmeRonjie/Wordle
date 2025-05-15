package com.ronjie.wordle.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun BoxScope.GameOverScreen(
    state: GameState,
    shownLost: () -> Unit
) {
    AnimatedVisibility(
        visible = state.game.isOver,
        modifier = Modifier.fillMaxSize(),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x7F000000))
                .clickable { shownLost() }
                .padding(16.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You lost. Tap to retry.",
                modifier = Modifier.padding(32.dp),
                color = MaterialTheme.colorScheme.onError,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
