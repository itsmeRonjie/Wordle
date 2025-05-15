package com.ronjie.wordle.presentation.home

import androidx.lifecycle.ViewModel
import com.ronjie.wordle.domain.model.Game
import com.ronjie.wordle.domain.model.Guess
import com.ronjie.wordle.domain.model.Word
import com.ronjie.wordle.domain.model.WordStatus
import com.ronjie.wordle.domain.use_cases.GetWordStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class GameViewModel @Inject constructor(
    initialGame: Game,
    private val getWordStatus: GetWordStatus
) : ViewModel() {

    private val _gameState = MutableStateFlow(GameState(initialGame))
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    fun characterEntered(character: Char) {
        if (wordIsEnteredCompletely()) return
        _gameState.update { currentState ->
            currentState.copy(
                currentlyEnteringWord = (currentState.currentlyEnteringWord
                    ?: "") + character.uppercaseChar()
            )
        }
    }

    private fun wordIsEnteredCompletely() =
        _gameState.value.currentlyEnteringWord?.length == _gameState.value.game.wordLength

    fun backspacePressed() {
        _gameState.update { currentState ->
            val newWord = when {
                currentState.currentlyEnteringWord == null -> null
                currentState.currentlyEnteringWord.length == 1 -> null
                else -> currentState.currentlyEnteringWord.dropLast(1)
            }
            currentState.copy(currentlyEnteringWord = newWord)
        }
    }

    fun submit() {
        if (!wordIsEnteredCompletely()) return
        val currentState = _gameState.value
        val word = Word(currentState.currentlyEnteringWord!!)
        val status = getWordStatus.execute(word, currentState.game.originalWord)

        _gameState.update {
            val newGuesses = if (status != WordStatus.NotExists) it.game.guesses + Guess(
                word, status
            ) else it.game.guesses
            it.copy(
                game = it.game.copy(guesses = newGuesses),
                currentlyEnteringWord = if (status != WordStatus.NotExists) null else it.currentlyEnteringWord,
                doesNotExist = if (status == WordStatus.NotExists) true else it.doesNotExist
            )
        }
    }

    fun shownNotExists() {
        _gameState.update { it.copy(doesNotExist = false) }
    }

    fun shownLost() {
        _gameState.update {
            it.copy(
                game = it.game.copy(guesses = listOf()),
                currentlyEnteringWord = null,
                doesNotExist = false
            )
        }
    }
}
