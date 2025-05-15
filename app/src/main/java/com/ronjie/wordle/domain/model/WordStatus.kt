package com.ronjie.wordle.domain.model

sealed class WordStatus {
    data object NotExists : WordStatus()
    data object Correct : WordStatus()
    data class Incorrect(
        val equalityStatuses: Array<EqualityStatus>
    ) : WordStatus() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Incorrect

            return equalityStatuses.contentEquals(other.equalityStatuses)
        }

        override fun hashCode(): Int {
            return equalityStatuses.contentHashCode()
        }
    }
}

enum class EqualityStatus {
    WrongPosition,
    Correct,
    Incorrect
}
