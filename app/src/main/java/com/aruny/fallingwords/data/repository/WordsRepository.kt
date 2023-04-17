package com.aruny.fallingwords.data.repository

import com.aruny.fallingwords.data.model.WordTranslationPair
import com.aruny.fallingwords.data.rest.WordsApi
import javax.inject.Inject

interface WordsRepository {
    /**
     * Gets a list of [WordTranslationPair] from the data source.
     *
     * @return List of [WordTranslationPair]
     */
    suspend fun getWords(): List<WordTranslationPair>
}

class WordsRepositoryImpl @Inject constructor(
    private val wordsApi: WordsApi
) : WordsRepository {

    // TODO: Handle network failures
    override suspend fun getWords(): List<WordTranslationPair> {
        return wordsApi.getWords()
    }
}
