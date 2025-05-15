package com.ronjie.wordle.presentation.home

import com.ronjie.wordle.domain.model.Level

data class LevelState(
    val currentLevel: Level? = null,
    val lastLevelReached: Boolean = false
)
