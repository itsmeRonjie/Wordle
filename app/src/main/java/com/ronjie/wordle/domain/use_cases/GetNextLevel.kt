package com.ronjie.wordle.domain.use_cases

import com.ronjie.wordle.domain.model.Level
import com.ronjie.wordle.domain.repository.LevelRepository
import com.ronjie.wordle.domain.repository.WordRepository

class GetNextLevel(
    private val wordRepository: WordRepository,
    private val levelRepository: LevelRepository
) {
    fun execute(): Level? {
        val currentLevelNumber = levelRepository.getCurrentLevelNumber()
        if (currentLevelNumber >= wordRepository.lastLevel + 1) return null
        return Level(currentLevelNumber, wordRepository.getWordForLevel(currentLevelNumber))
    }
}
