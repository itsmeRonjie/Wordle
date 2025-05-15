package com.ronjie.wordle.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.ronjie.wordle.domain.model.Game
import com.ronjie.wordle.domain.model.Level
import com.ronjie.wordle.domain.use_cases.GetWordStatus

@Composable
internal fun WordScreen(
    level: Level,
    getWordStatus: GetWordStatus,
    levelCompleted: () -> Unit
) {
    val word = level.word
    val viewModel = remember(word) {
        val initialGame = Game(
            originalWord = word,
            guesses = listOf(),
            wordLength = 5
        )
        GameViewModel(initialGame, getWordStatus)
    }

    val state by viewModel.gameState.collectAsState()
    GameScreen(
        level = level,
        state = state,
        onKey = { viewModel.characterEntered(it) },
        onBackspace = { viewModel.backspacePressed() },
        onSubmit = { viewModel.submit() },
        shownError = { viewModel.shownNotExists() },
        shownWon = levelCompleted,
        shownLost = { viewModel.shownLost() }
    )
}
