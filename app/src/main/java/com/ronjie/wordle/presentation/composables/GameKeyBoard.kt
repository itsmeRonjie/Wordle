package com.ronjie.wordle.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ronjie.wordle.domain.model.EqualityStatus
import com.ronjie.wordle.domain.model.KeyboardKeys
import com.ronjie.wordle.presentation.home.GameState

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
internal fun GameKeyboard(
    state: GameState,
    modifier: Modifier = Modifier,
    onKey: (char: Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit
) {
    BoxWithConstraints(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(10) {
                    val key = state.game.availableKeyboard.keys[it]
                    KeyboardKey(
                        key = key,
                        onKey = onKey,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.size(4.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(9) {
                    val key = state.game.availableKeyboard.keys[10 + it]
                    KeyboardKey(
                        key = key,
                        onKey = onKey,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.size(4.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                repeat(7) {
                    val key = state.game.availableKeyboard.keys[19 + it]
                    KeyboardKey(
                        key = key,
                        onKey = onKey,
                        modifier = Modifier.weight(1f)
                    )
                }

                KeyboardKey(
                    text = "⌫",
                    modifier = Modifier.width(40.dp),
                    onClick = onBackspace
                )
            }

            Spacer(Modifier.size(16.dp))

            Button(
                onClick = onSubmit,
                modifier = modifier.padding(horizontal = 4.dp),
                enabled = state.currentlyEnteringWord?.length == state.game.wordLength,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "CHECK",
                    fontWeight = FontWeight.Black
                )
            }
            Spacer(Modifier.size(6.dp))
        }
    }
}

@Composable
private fun KeyboardKey(
    key: KeyboardKeys.Key,
    onKey: (char: Char) -> Unit,
    modifier: Modifier = Modifier
) {
    KeyboardKey(
        text = key.button.toString().uppercase(),
        modifier = modifier,
        status = key.equalityStatus
    ) { onKey(key.button) }
}

@Composable
private fun KeyboardKey(
    text: String,
    modifier: Modifier = Modifier,
    status: EqualityStatus? = null,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        targetValue = when (status) {
            EqualityStatus.Incorrect -> Color(0xFF642424)
            else -> Color(0xFF393939)
        }
    )

    val testColor by animateColorAsState(
        targetValue = when (status) {
            EqualityStatus.WrongPosition -> Color(0xFFFFD166)
            EqualityStatus.Correct -> Color(0xFF73D154)
            EqualityStatus.Incorrect, null -> Color(0xFFE7E7E7)
        }
    )

    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color)
            .clickable(onClick = { onClick() }),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier,
            color = testColor,
            fontSize = 24.sp
        )
    }
}
