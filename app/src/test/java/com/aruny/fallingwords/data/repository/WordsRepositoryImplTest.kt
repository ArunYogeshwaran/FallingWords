package com.aruny.fallingwords.data.repository

import com.aruny.fallingwords.common.getTestWordTranslationPairs
import com.aruny.fallingwords.data.rest.WordsApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class WordsRepositoryImplTest {
    private val wordsApi: WordsApi = mock()

    private lateinit var wordsRepository: WordsRepository

    @Test
    fun `verify the list of words given there are no failures`() = runTest {
        val testList = getTestWordTranslationPairs(4)
        doReturn(testList).whenever(wordsApi).getWords()
        wordsRepository = WordsRepositoryImpl(wordsApi)
        assertThat(testList, `is`(wordsRepository.getWords()))
    }
}