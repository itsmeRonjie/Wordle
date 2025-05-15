package com.ronjie.wordle.domain.repository

import com.ronjie.wordle.domain.model.Level

interface LevelRepository {
    fun getCurrentLevelNumber(): Long
    fun levelPassed(level: Level)
    fun reset()
}
