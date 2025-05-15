package com.ronjie.wordle.presentation.home

import androidx.lifecycle.ViewModel
import com.ronjie.wordle.domain.repository.LevelRepository
import com.ronjie.wordle.domain.use_cases.GetNextLevel
import com.ronjie.wordle.domain.use_cases.ResetLevels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LevelsViewModel @Inject constructor(
    private val levelRepository: LevelRepository,
    private val getNextLevel: GetNextLevel,
    private val resetLevels: ResetLevels,
) : ViewModel() {

    private val _state = MutableStateFlow(LevelState())
    val state: StateFlow<LevelState> = _state.asStateFlow()

    init {
        updateLevel()
    }

    fun levelPassed() {
        _state.value.currentLevel?.let { levelRepository.levelPassed(it) }
        updateLevel()
    }

    private fun updateLevel() {
        val nextLevel = getNextLevel.execute()
        _state.update {
            if (nextLevel == null) {
                it.copy(lastLevelReached = true, currentLevel = null)
            } else {
                it.copy(currentLevel = nextLevel, lastLevelReached = false)
            }
        }
    }

    fun reset() {
        resetLevels.execute()
        updateLevel()
    }
}
