package com.aruny.fallingwords.data

import retrofit2.http.GET

interface WordsRemoteService {
    @GET(WORDS_PATH)
    suspend fun getWords(): List<WordTranslationPair>
}
