package com.ronjie.wordle.domain.model

data class Guess(
    val word: Word,
    val wordStatus: WordStatus
)
