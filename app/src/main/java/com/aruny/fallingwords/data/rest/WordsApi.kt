package com.aruny.fallingwords.data.rest

import com.aruny.fallingwords.data.model.WordTranslationPair
import retrofit2.http.GET

interface WordsApi {
    @GET(WORDS_PATH)
    suspend fun getWords(): List<WordTranslationPair>

    companion object {
        private const val WORDS_PATH =
            "DroidCoder/7ac6cdb4bf5e032f4c737aaafe659b33/raw/baa9fe0d586082d85db71f346e2b039c580c5804/words.json"
    }
}