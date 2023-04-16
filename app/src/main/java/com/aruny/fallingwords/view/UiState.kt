package com.aruny.fallingwords.view

sealed class UiState {
    data class FetchingWords(val score: Int, val lives: Int) : UiState()

    object WordsFetched : UiState()

    data class CorrectAnswer(val score: Int) : UiState()

    data class IncorrectAnswer(val lives: Int) : UiState()

    data class NextWord(val durationMilliSecs: Long) : UiState()

    data class GameOver(val score: Int) : UiState()
}