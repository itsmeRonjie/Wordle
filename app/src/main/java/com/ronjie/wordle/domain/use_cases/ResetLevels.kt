package com.ronjie.wordle.domain.use_cases

import com.ronjie.wordle.domain.repository.LevelRepository

class ResetLevels(
    private val levelRepository: LevelRepository
) {
    fun execute() {
        levelRepository.reset()
    }
}
