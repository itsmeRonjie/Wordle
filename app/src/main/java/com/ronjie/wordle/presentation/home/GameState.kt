package com.ronjie.wordle.presentation.home

import com.ronjie.wordle.domain.model.Game

data class GameState(
    val game: Game,
    val currentlyEnteringWord: String? = null,
    val doesNotExist: Boolean = false
)
