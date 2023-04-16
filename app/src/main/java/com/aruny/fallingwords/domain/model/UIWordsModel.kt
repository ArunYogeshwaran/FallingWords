package com.aruny.fallingwords.domain.model

/**
 * @property englishWord The English word.
 * @property spanishWord The Spanish word that can be either correct or the incorrect translation of the English word.
 * @property isCorrectTranslation This is true if the Spanish word is the correct translation of the English word.
 */
data class UIWordsModel(
    val englishWord: String,
    val spanishWord: String,
    val isCorrectTranslation: Boolean
)
