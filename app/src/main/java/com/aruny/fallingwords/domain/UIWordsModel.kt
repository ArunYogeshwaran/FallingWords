package com.aruny.fallingwords.domain


const val TOTAL_OPTIONS_COUNT = 3

/**
 * @property englishWord The English word.
 * @property correctTranslation The correct translation for the English word from the other language.
 * @property optionWords This is the set of [TOTAL_OPTIONS_COUNT] words with one correct option
 * and [TOTAL_OPTIONS_COUNT - 1] wrong options.
 */
data class UIWordsModel(
    val englishWord: String,
    val correctTranslation: String,
    val optionWords: List<String>
)
