package com.ronjie.wordle.domain.repository

import com.ronjie.wordle.domain.model.Word

interface WordRepository {
    val lastLevel: Long
    fun find(word: Word): Boolean
    fun random(): Word
    fun getWordForLevel(currentLevelNumber: Long): Word
}
