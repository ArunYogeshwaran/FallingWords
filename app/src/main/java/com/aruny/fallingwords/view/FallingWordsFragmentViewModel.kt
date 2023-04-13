package com.aruny.fallingwords.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruny.fallingwords.data.WordTranslationPair
import com.aruny.fallingwords.data.WordsRepository
import com.aruny.fallingwords.domain.UIWordsModel
import com.aruny.fallingwords.domain.WordMixerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FallingWordsFragmentViewModel : ViewModel() {
    private val wordMixerUseCase = WordMixerUseCase(WordsRepository())

    private val _wordPairLiveData = MutableLiveData<List<UIWordsModel>>()
    val wordPairLiveData: LiveData<List<UIWordsModel>> = _wordPairLiveData

    fun getWords() {
        viewModelScope.launch(Dispatchers.IO) {
            val words = wordMixerUseCase.fetchWords()
            _wordPairLiveData.postValue(words)
            println(words)
        }
    }
}