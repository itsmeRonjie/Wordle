package com.ronjie.wordle.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ronjie.wordle.domain.model.EqualityStatus
import com.ronjie.wordle.domain.model.WordStatus
import com.ronjie.wordle.presentation.home.GameState

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun GameGrid(
    state: GameState,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            repeat(6) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) { column ->
                        val character: Char?
                        val status: EqualityStatus?
                        if (row < state.game.guesses.size) {
                            val guess = state.game.guesses[row]
                            character = guess.word.word[column]
                            status = when (guess.wordStatus) {
                                WordStatus.Correct -> EqualityStatus.Correct
                                is WordStatus.Incorrect -> guess.wordStatus.equalityStatuses[column]
                                WordStatus.NotExists -> EqualityStatus.Incorrect
                            }
                        } else {
                            character =
                                if (row == state.game.guesses.size) {
                                    state.currentlyEnteringWord?.getOrNull(column)
                                } else null
                            status = null
                        }

                        WordCharacterBox(
                            character = character,
                            status = status,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
