package com.ronjie.wordle.presentation.composables

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.ronjie.wordle.R
import com.ronjie.wordle.domain.model.Level

@Composable
internal fun ColumnScope.GameHeader(level: Level, modifier: Modifier = Modifier) {
    var revealing by remember(level) { mutableStateOf(false) }
    GameHeader(modifier) {
        LevelHeaderContent(level, revealing) {
            revealing = it
        }
    }
}

@Composable
internal fun ColumnScope.GameHeader(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .clickable {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW)
                        .apply { data = "https://github.com/itsmeRonjie/Wordle".toUri() }
                )
            }
            .align(Alignment.CenterHorizontally)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "github.com/itsmeRonjie/Wordle",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 10.sp,
            style = MaterialTheme.typography.bodySmall
        )
        content()
    }
}

@Composable
private fun ColumnScope.LevelHeaderContent(
    level: Level,
    revealing: Boolean,
    onRevealChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .align(Alignment.CenterHorizontally)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Level ${level.number}",
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Serif,
            style = MaterialTheme.typography.headlineSmall
        )
        Box(Modifier.padding(start = 16.dp)) {
            if (!revealing) {
                Text(
                    text = "(reveal)",
                    modifier = Modifier.clickable { onRevealChanged(true) },
                    style = MaterialTheme.typography.labelSmall
                )
            } else {
                Text(
                    text = level.word.word,
                    modifier = Modifier.clickable { onRevealChanged(false) },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
