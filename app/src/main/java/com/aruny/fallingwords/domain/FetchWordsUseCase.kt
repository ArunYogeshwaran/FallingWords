package com.aruny.fallingwords.domain

import com.aruny.fallingwords.data.WordTranslationPair
import com.aruny.fallingwords.data.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchWordsUseCase(private val wordsRepository: WordsRepository) {
    suspend fun fetchWords(): List<WordTranslationPair> {
        return withContext(Dispatchers.IO) {
            return@withContext wordsRepository.getWords()
        }
    }
}
