package com.aruny.fallingwords.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruny.fallingwords.domain.WordMixerUseCase
import com.aruny.fallingwords.domain.model.UIWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FallingWordsFragmentViewModel @Inject constructor(
    private val wordMixerUseCase: WordMixerUseCase
) : ViewModel() {
    private var currentScore = DEFAULT_SCORE
        set(value) {
            field = value
            _scoreStateFlow.value = value
        }

    private var currentLives = MAX_LIVES
        set(value) {
            field = value
            _livesStateFlow.value = value
        }

    private var wordsList: List<UIWordsModel> = emptyList()
    private var currentWordIndex = DEFAULT_WORD_INDEX

    private val _uiState = SingleLiveEvent<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _scoreStateFlow = MutableStateFlow(currentScore)
    val scoreStateFlow: StateFlow<Int> = _scoreStateFlow.asStateFlow()

    private val _livesStateFlow = MutableStateFlow(currentLives)
    val livesStateFlow: StateFlow<Int> = _livesStateFlow.asStateFlow()

    private val _currentWordFlow = MutableStateFlow(UIWordsModel.NOT_LOADED)
    val currentWordFlow: StateFlow<UIWordsModel> = _currentWordFlow.asStateFlow()

    fun startGame() {
        fetchWords()

        currentWordIndex = DEFAULT_WORD_INDEX
        currentScore = DEFAULT_SCORE
        currentLives = MAX_LIVES
        _currentWordFlow.value = UIWordsModel.NOT_LOADED
    }

    private fun fetchWords() {
        _uiState.value = UiState.FetchingWords(DEFAULT_SCORE, MAX_LIVES)
        viewModelScope.launch(Dispatchers.IO) {
            wordsList = wordMixerUseCase.fetchWords()
            _uiState.postValue(UiState.WordsFetched)
        }
    }

    fun getNextWord() {
        if (wordsList.isEmpty()) {
            return
        }

        // Ensure we end the game when all the words are done
        if (currentWordIndex < wordsList.size) {
            currentWordIndex++
            _currentWordFlow.value = wordsList[currentWordIndex]
            _uiState.value = UiState.NextWord(TRANSITION_DURATION_IN_MILLIS)
        } else {
            handleGameOver()
        }
    }

    fun checkAnswer(shouldBeCorrect: Boolean) {
        val word = wordsList[currentWordIndex]
        val isAnswerCorrect = word.isCorrectTranslation == shouldBeCorrect
        if (isAnswerCorrect) {
            handleCorrectAnswer()
        } else {
            handleIncorrectAnswer()
        }
    }

    private fun handleCorrectAnswer() {
        currentScore++
        _uiState.value = UiState.CorrectAnswer(currentScore)
        getNextWord()
    }

    private fun handleIncorrectAnswer() {
        currentLives--
        if (currentLives == 0) {
            handleGameOver()
        } else {
            _uiState.value = UiState.IncorrectAnswer(currentLives)
            getNextWord()
        }
    }

    fun handleNoResponseFromUser() {
        handleIncorrectAnswer()
    }

    private fun handleGameOver() {
        _uiState.value = UiState.GameOver(currentScore)
    }

    companion object {
        private const val DEFAULT_WORD_INDEX = -1
        private const val DEFAULT_SCORE = 0
        private const val MAX_LIVES = 3
        private const val TRANSITION_DURATION_IN_MILLIS = 5500L
    }
}