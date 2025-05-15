package com.ronjie.wordle.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.ronjie.wordle.domain.model.Game
import com.ronjie.wordle.domain.model.Word
import com.ronjie.wordle.domain.repository.AssetFileWordRepository
import com.ronjie.wordle.domain.repository.LevelRepository
import com.ronjie.wordle.domain.repository.LocalStorageLevelRepository
import com.ronjie.wordle.domain.repository.WordRepository
import com.ronjie.wordle.domain.use_cases.GetNextLevel
import com.ronjie.wordle.domain.use_cases.GetWordStatus
import com.ronjie.wordle.domain.use_cases.ResetLevels
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAssetManager(
        @ApplicationContext context: Context
    ): AssetManager {
        return context.assets
    }

    @Singleton
    @Provides
    fun provideAssetFileWordRepo(
        assetManager: AssetManager
    ): WordRepository {
        return AssetFileWordRepository(assetManager)
    }

    @Provides
    fun provideGame(): Game {
        return Game(
            originalWord = Word("Test"),
            guesses = emptyList(),
            wordLength = 4
        )
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideLocalStorageLevelRepo(
        sharedPreferences: SharedPreferences
    ): LevelRepository {
        return LocalStorageLevelRepository(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideGetNextLevelUseCase(
        wordRepository: WordRepository,
        levelRepository: LevelRepository
    ): GetNextLevel {
        return GetNextLevel(wordRepository, levelRepository)
    }

    @Singleton
    @Provides
    fun provideGetWordStatusUseCase(
        wordRepository: WordRepository
    ): GetWordStatus {
        return GetWordStatus(wordRepository)
    }

    @Singleton
    @Provides
    fun provideResetLevelsCase(
        levelRepository: LevelRepository
    ): ResetLevels {
        return ResetLevels(levelRepository)
    }
}
