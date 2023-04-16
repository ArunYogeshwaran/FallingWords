package com.aruny.fallingwords.domain.model

/**
 * @property englishWord The English word.
 * @property otherLangWord The other language word that can be either correct or the incorrect translation of the English word.
 * @property isCorrectTranslation This is true if the Spanish word is the correct translation of the English word.
 */
data class UIWordsModel(
    val englishWord: String,
    val otherLangWord: String,
    val isCorrectTranslation: Boolean
) {
    companion object {
        val NOT_LOADED = UIWordsModel("", "", false)
    }
}
