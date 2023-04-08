package com.aruny.fallingwords.domain

import com.aruny.fallingwords.data.WordsRepository

class FetchWordsUseCase(private val wordsRepository: WordsRepository) {
    fun fetchWords() {
        wordsRepository.getWords()
    }

}
