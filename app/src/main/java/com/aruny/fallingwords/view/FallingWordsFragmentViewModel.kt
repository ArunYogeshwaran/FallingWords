package com.aruny.fallingwords.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruny.fallingwords.data.WordsRepository
import com.aruny.fallingwords.domain.FetchWordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FallingWordsFragmentViewModel : ViewModel() {
    private val fetchWordsUseCase = FetchWordsUseCase(WordsRepository())

    fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = fetchWordsUseCase.fetchWords()
            println(words)
        }
    }
}