package com.aruny.fallingwords.domain.usecase

import com.aruny.fallingwords.common.getTestWordTranslationPairs
import com.aruny.fallingwords.data.repository.WordsRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class WordMixerUseCaseTest {
    private val wordsRepositoryMock: WordsRepository = mock()

    private lateinit var wordMixerUseCase: WordMixerUseCase

    @Before
    fun setUp() {
        wordMixerUseCase = WordMixerUseCase(wordsRepositoryMock)
    }

    @Test
    fun `verify english words from the list of mixed words`() = runTest {
        val list = getTestWordTranslationPairs(3)
        doReturn(list).`when`(wordsRepositoryMock).getWords()

        val resultList = wordMixerUseCase.fetchWords()
        assertThat(list.size, `is`(resultList.size))
        assertThat(list[0].textEng, `is`(resultList[0].englishWord))
        assertThat(list[1].textEng, `is`(resultList[1].englishWord))
        assertThat(list[2].textEng, `is`(resultList[2].englishWord))
    }
}