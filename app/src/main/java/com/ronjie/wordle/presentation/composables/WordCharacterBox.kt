package com.ronjie.wordle.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronjie.wordle.domain.model.EqualityStatus

@Composable
internal fun WordCharacterBox(
    character: Char?,
    status: EqualityStatus?,
    modifier: Modifier = Modifier
) {
    val color = when (status) {
        EqualityStatus.WrongPosition -> Color(0xFFFFD166)
        EqualityStatus.Correct -> Color(0xFF73D154)
        EqualityStatus.Incorrect -> Color(0xFFFF6B6B)
        null -> Color.Transparent
    }

    val textColor = when (status) {
        null -> MaterialTheme.colorScheme.onBackground
        else -> MaterialTheme.colorScheme.onPrimary
    }
    val borderModifier = if (status == null) Modifier.border(1.dp, Color(0xFFFF6B6B)) else Modifier
    BasicCharacterBox(modifier, borderModifier, color, character, textColor)
}

@Preview
@Composable
internal fun CharacterBoxPreview() {
    Row {
        WordCharacterBox(character = 'A', status = null)
        WordCharacterBox(character = 'D', status = EqualityStatus.Incorrect)
        WordCharacterBox(character = 'I', status = EqualityStatus.WrongPosition)
        WordCharacterBox(character = 'B', status = EqualityStatus.Correct)
    }
}
