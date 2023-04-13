package com.aruny.fallingwords.domain

import com.aruny.fallingwords.data.WordTranslationPair
import com.aruny.fallingwords.data.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordMixerUseCase(private val wordsRepository: WordsRepository) {
    suspend fun fetchWords(): List<UIWordsModel> {
        return withContext(Dispatchers.IO) {
            val wordTranslationPairs = wordsRepository.getWords()
            return@withContext wordTranslationPairs.map {
                UIWordsModel(
                    englishWord = it.textEng,
                    correctTranslation = it.textSpa,
                    optionWords = getOptions(it.textSpa, wordTranslationPairs)
                )
            }
        }
    }

    private fun getOptions(
        correctOption: String,
        wordTranslationPairs: List<WordTranslationPair>
    ): List<String> {
        val mutableList = mutableListOf<String>()
        mutableList.add(correctOption)
        wordTranslationPairs.shuffled().take(TOTAL_OPTIONS_COUNT - 1).forEach {
            mutableList.add(it.textSpa)
        }
        return mutableList
    }
}
