package com.ronjie.wordle.domain.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ronjie.wordle.domain.model.Level
import kotlin.math.max

class LocalStorageLevelRepository(
    private val sharedPreferences: SharedPreferences
) : LevelRepository {
    private var lastLevel: Long
        get() {
            return sharedPreferences.getLong("LastLevel", 1)
        }
        set(value) {
            sharedPreferences.edit(commit = true) { putLong("LastLevel", value) }
        }

    override fun getCurrentLevelNumber(): Long {
        return lastLevel
    }

    override fun levelPassed(level: Level) {
        val settingLevel = max(level.number + 1, lastLevel)
        lastLevel = settingLevel
    }

    override fun reset() {
        lastLevel = 1
    }
}
