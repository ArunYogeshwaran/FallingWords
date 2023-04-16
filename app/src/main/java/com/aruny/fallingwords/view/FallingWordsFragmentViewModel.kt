package com.aruny.fallingwords.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruny.fallingwords.domain.WordMixerUseCase
import com.aruny.fallingwords.domain.model.UIWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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

    private val _timerStateFlow = MutableStateFlow(DEFAULT_TIME)
    val timerStateFlow: StateFlow<Int> = _timerStateFlow.asStateFlow()

    private var timerJob: Job? = null

    fun startGame() {
        fetchWords()

        currentWordIndex = DEFAULT_WORD_INDEX
        currentScore = DEFAULT_SCORE
        currentLives = MAX_LIVES
        _currentWordFlow.value = UIWordsModel.NOT_LOADED
        resetTimer()
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
            val countdownTimer = getCountdown(currentWordIndex)
            _uiState.value = UiState.NextWord(countdownTimer.toLong() * SECS_TO_MILLI_SECS)
            startTimer(countdownTimer)
        } else {
            handleGameOver()
        }
    }

    private fun getCountdown(currentWordIndex: Int): Int {
        val reducingFactor = getReducingFactor(currentWordIndex)
        val countDown = ANIMATION_DURATION_IN_SECS - reducingFactor
        return if (countDown > MINIMUM_COUNTDOWN_SECS) {
            countDown
        } else {
            MINIMUM_COUNTDOWN_SECS
        }
    }

    private fun getReducingFactor(currentWordIndex: Int): Int {
        val effectiveDivider = wordsList.size / LEVEL_DIVIDER
        return (currentWordIndex + 1) / effectiveDivider
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
        resetTimer()
        currentScore++
        _uiState.value = UiState.CorrectAnswer(currentScore)
        getNextWord()
    }

    private fun resetTimer() {
        timerJob?.cancel()
        _timerStateFlow.value = DEFAULT_TIME
    }

    private fun handleIncorrectAnswer() {
        resetTimer()
        _timerStateFlow.value = DEFAULT_TIME
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
        timerJob?.cancel()
        _uiState.value = UiState.GameOver(currentScore)
    }

    private fun startTimer(seconds: Int) {
        timerJob = viewModelScope.launch {
            (seconds - 1 downTo 0).asFlow()
                .onEach { delay(TIMER_DELAY_IN_MILLIS) }
                .onStart { emit(seconds) }
                .conflate()
                .flowOn(Dispatchers.Default)
                .collect {
                    _timerStateFlow.value = it
                }
        }
    }

    companion object {
        private const val DEFAULT_WORD_INDEX = -1
        private const val DEFAULT_SCORE = 0
        private const val DEFAULT_TIME = 0
        private const val MAX_LIVES = 3

        /**
         * Divider used to decide when to increase the speed of words.
         *
         * If there are 250 words in total, the speed will increase for every 25th(250/10) word
         */
        private const val LEVEL_DIVIDER = 10

        private const val TIMER_DELAY_IN_MILLIS = 1000L
        private const val ANIMATION_DURATION_IN_SECS = 10
        private const val SECS_TO_MILLI_SECS = 1000L
        private const val MINIMUM_COUNTDOWN_SECS = 3
    }
}