package com.ronjie.wordle.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun BasicCharacterBox(
    modifier: Modifier = Modifier,
    borderModifier: Modifier,
    color: Color,
    character: Char?,
    textColor: Color
) {
    var lastChar by remember { mutableStateOf<Char?>(null) }
    if (character != null) {
        lastChar = character
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(2.dp))
            .then(borderModifier)
            .background(animateColorAsState(targetValue = color).value),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(character != null) {
            Text(
                text = lastChar?.uppercase() ?: "",
                color = animateColorAsState(targetValue = textColor).value,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                )
            )
        }
    }
}
