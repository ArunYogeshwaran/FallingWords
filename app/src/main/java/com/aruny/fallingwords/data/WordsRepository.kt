package com.aruny.fallingwords.data

import com.aruny.fallingwords.data.rest.WordsApi
import javax.inject.Inject

interface WordsRepository {
    suspend fun getWords(): List<WordTranslationPair>
}

class WordsRepositoryImpl @Inject constructor(
    private val wordsApi: WordsApi
) :
    WordsRepository {

    override suspend fun getWords(): List<WordTranslationPair> {
        return wordsApi.getWords()
    }
}
