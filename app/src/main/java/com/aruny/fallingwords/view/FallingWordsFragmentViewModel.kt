package com.aruny.fallingwords.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aruny.fallingwords.domain.WordMixerUseCase
import com.aruny.fallingwords.domain.model.UIWordsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FallingWordsFragmentViewModel @Inject constructor(
    private val wordMixerUseCase: WordMixerUseCase
) : ViewModel() {

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