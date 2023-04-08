package com.aruny.fallingwords.data

class WordsRepository {
    private val wordsRemoteService = WordsRemoteServiceImpl()

    suspend fun getWords(): List<WordTranslationPair> {
        return wordsRemoteService.getWords()
    }
}
