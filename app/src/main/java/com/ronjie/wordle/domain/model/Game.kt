package com.ronjie.wordle.domain.model

data class Game(
    val originalWord: Word,
    val guesses: List<Guess>,
    val wordLength: Int = 5,
    private val keyboardKeys: KeyboardKeys = KeyboardKeys.English(),
) {
    val availableKeyboard: KeyboardKeys = keyboardKeys.updateWithGuesses(guesses)
    val isWon = guesses.any { it.wordStatus == WordStatus.Correct }
    val isOver = guesses.size == 6 && !isWon
}

private fun KeyboardKeys.updateWithGuesses(guesses: List<Guess>): KeyboardKeys {
    val allWords: Map<Char, List<Pair<Char, EqualityStatus?>>> =
        guesses.flatMap { guess ->
            guess.word.word.mapIndexed { index, character ->
                when (guess.wordStatus) {
                    WordStatus.Correct -> Pair(character, EqualityStatus.Correct)
                    is WordStatus.Incorrect -> Pair(
                        character,
                        guess.wordStatus.equalityStatuses[index]
                    )

                    WordStatus.NotExists -> Pair(character, null)
                }
            }
        }.toSet().groupBy { it.first.uppercaseChar() }

    val keys = keys.map { key ->

        val states = allWords[key.button.uppercaseChar()]
        val isIncorrect =
            states?.all { it.second == EqualityStatus.Incorrect }
        val equalityStatus = when {
            states?.any { it.second == EqualityStatus.Correct } == true -> EqualityStatus.Correct
            states?.any { it.second == EqualityStatus.WrongPosition } == true -> EqualityStatus.WrongPosition
            states?.all { it.second == EqualityStatus.Incorrect } == true -> EqualityStatus.Incorrect
            else -> null
        }
        key.copy(equalityStatus = equalityStatus)
    }

    return withUpdatedButton(keys)
}

fun Game.copyWithNewGuess(
    guess: Guess,
) = copy(guesses = guesses + guess)
