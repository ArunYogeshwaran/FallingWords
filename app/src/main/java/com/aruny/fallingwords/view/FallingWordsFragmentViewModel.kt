package com.aruny.fallingwords.view

import androidx.lifecycle.ViewModel
import com.aruny.fallingwords.data.WordsRepository
import com.aruny.fallingwords.domain.FetchWordsUseCase

class FallingWordsFragmentViewModel : ViewModel() {
    private val fetchWordsUseCase = FetchWordsUseCase(WordsRepository())
    init {
        fetchWordsUseCase.fetchWords()
    }
}