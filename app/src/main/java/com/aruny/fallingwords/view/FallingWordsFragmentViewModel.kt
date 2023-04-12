package com.aruny.fallingwords.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruny.fallingwords.data.WordTranslationPair
import com.aruny.fallingwords.data.WordsRepository
import com.aruny.fallingwords.domain.FetchWordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FallingWordsFragmentViewModel : ViewModel() {
    private val fetchWordsUseCase = FetchWordsUseCase(WordsRepository())

    private val _wordPairLiveData = MutableLiveData<List<WordTranslationPair>>()
    val wordPairLiveData: LiveData<List<WordTranslationPair>> = _wordPairLiveData

    fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = fetchWordsUseCase.fetchWords()
            _wordPairLiveData.postValue(words)
            println(words)
        }
    }
}