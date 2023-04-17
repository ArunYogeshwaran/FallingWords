package com.aruny.fallingwords.common

import com.aruny.fallingwords.data.model.WordTranslationPair
import com.aruny.fallingwords.domain.model.UIWordsModel
import java.util.*

fun getTestWordTranslationPairs(@androidx.annotation.IntRange(from = 1) count: Int)
        : List<WordTranslationPair> {
    val testList = mutableListOf<WordTranslationPair>()
    (0..count).forEach {
        testList.add(WordTranslationPair("Language $it", "Other Language $it"))
    }
    return testList.toList()
}

fun getTestUIWords(@androidx.annotation.IntRange(from = 1) count: Int): List<UIWordsModel> {
    val testList = mutableListOf<UIWordsModel>()
    (0..count).forEach {
        testList.add(
            UIWordsModel(
                "Language $it",
                "Other Language $it", Random().nextBoolean()
            )
        )
    }
    return testList.toList()
}