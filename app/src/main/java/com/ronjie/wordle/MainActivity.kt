package com.ronjie.wordle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ronjie.wordle.domain.repository.AssetFileWordRepository
import com.ronjie.wordle.domain.use_cases.GetWordStatus
import com.ronjie.wordle.presentation.composables.GameHeader
import com.ronjie.wordle.presentation.home.LevelsViewModel
import com.ronjie.wordle.presentation.home.WordScreen
import com.ronjie.wordle.presentation.theme.WordleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WordleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val assetWordRepository = remember {
                        AssetFileWordRepository(assets)
                    }

                    val getWordStatus = remember {
                        GetWordStatus(assetWordRepository)
                    }

                    val levelViewModel: LevelsViewModel by viewModels()

                    val level = levelViewModel.state.collectAsState().value.currentLevel

                    if (level != null) {
                        WordScreen(level, getWordStatus) {
                            levelViewModel.levelPassed()
                        }
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                GameHeader {}
                                Text(
                                    text = "You have mastered the game (1024 levels)!",
                                    modifier = Modifier.padding(top = 32.dp),
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Text(
                                    text = "Want to reset to the first level?",
                                    modifier = Modifier.padding(top = 32.dp),
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Button(
                                    onClick = { levelViewModel.reset() },
                                    modifier = Modifier.padding(top = 16.dp),
                                ) {
                                    Text("Reset")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
