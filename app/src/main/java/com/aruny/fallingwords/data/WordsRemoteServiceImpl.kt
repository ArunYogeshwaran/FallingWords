package com.aruny.fallingwords.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordsRemoteServiceImpl: WordsRemoteService {
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WordsRemoteService::class.java)

    override suspend fun getWords(): List<WordTranslationPair> {
        return api.getWords()
    }
}