package com.aruny.fallingwords.data

import com.google.gson.annotations.SerializedName

data class WordTranslationPair(
    @SerializedName("text_eng")
    private val textEng: String,
    @SerializedName("text_spa")
    private val textSpa: String
) {
    override fun toString(): String {
        return "$textEng-->$textSpa"
    }
}