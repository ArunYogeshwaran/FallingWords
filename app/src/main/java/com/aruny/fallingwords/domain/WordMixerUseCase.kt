package com.aruny.fallingwords.domain

import com.aruny.fallingwords.data.WordTranslationPair
import com.aruny.fallingwords.data.WordsRepository
import com.aruny.fallingwords.domain.model.UIWordsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordMixerUseCase @Inject constructor(private val wordsRepository: WordsRepository) {
    suspend fun fetchWords(): List<UIWordsModel> {
        return withContext(Dispatchers.IO) {
            val wordTranslationPairs = wordsRepository.getWords()
            return@withContext wordTranslationPairs.map {
                val randomSpanishWord = getRandomSpanishWord(it.textSpa, wordTranslationPairs)
                UIWordsModel(
                    englishWord = it.textEng,
                    spanishWord = randomSpanishWord,
                    isCorrectTranslation = randomSpanishWord == it.textSpa
                )
            }
        }
    }

    private fun getRandomSpanishWord(
        correctTranslation: String,
        wordTranslationPairs: List<WordTranslationPair>
    ): String {
        val mutableList = mutableListOf<String>()
        mutableList.add(correctTranslation)
        wordTranslationPairs.shuffled().take(RANDOM_OPTIONS_COUNT).forEach {
            mutableList.add(it.textSpa)
        }
        return mutableList.random()
    }

    companion object {
        /**
         * Higher the number means lesser probability of getting the correct translation.
         */
        private const val RANDOM_OPTIONS_COUNT = 3
    }
}
