package com.aruny.fallingwords.data

import com.google.gson.annotations.SerializedName

data class WordTranslationPair(
    @SerializedName("text_eng")
    val textEng: String,
    @SerializedName("text_spa")
    val textSpa: String
) {
    override fun toString(): String {
        return "$textEng-->$textSpa"
    }
}