package com.aruny.fallingwords.domain.usecase

import com.aruny.fallingwords.data.model.WordTranslationPair
import com.aruny.fallingwords.data.repository.WordsRepository
import com.aruny.fallingwords.domain.model.UIWordsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordMixerUseCase @Inject constructor(private val wordsRepository: WordsRepository) {
    /**
     * Fetches the list of words the data source, creates and returns the word groups by randomly
     * mixing the option words.
     *
     * @return List of [UIWordsModel]
     */
    suspend fun fetchWords(): List<UIWordsModel> {
        return withContext(Dispatchers.IO) {
            val wordTranslationPairs = wordsRepository.getWords()
            return@withContext wordTranslationPairs.map {
                val randomSpanishWord = getRandomSpanishWord(it.textSpa, wordTranslationPairs)
                UIWordsModel(
                    englishWord = it.textEng,
                    otherLangWord = randomSpanishWord,
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
