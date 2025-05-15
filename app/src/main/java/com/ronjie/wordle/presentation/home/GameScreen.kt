package com.ronjie.wordle.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ronjie.wordle.domain.model.Level
import com.ronjie.wordle.presentation.composables.GameGrid
import com.ronjie.wordle.presentation.composables.GameHeader
import com.ronjie.wordle.presentation.composables.GameKeyboard

@Composable
fun GameScreen(
    level: Level,
    state: GameState,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    shownError: () -> Unit,
    shownWon: () -> Unit,
    shownLost: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Column(Modifier.padding(bottom = 8.dp)) {
            GameHeader(level)
            GameGrid(
                state = state,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
                    .fillMaxWidth(0.8f)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.size(16.dp))
            GameKeyboard(
                state = state,
                onKey = onKey,
                onBackspace = onBackspace,
                onSubmit = onSubmit
            )
        }
        ErrorScreen(state, shownError)
        WonScreen(state, shownWon)
        GameOverScreen(state, shownLost)
    }
}
