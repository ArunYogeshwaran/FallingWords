package com.aruny.fallingwords.view.fallingwords

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aruny.fallingwords.common.CoroutinesTestRule
import com.aruny.fallingwords.common.getTestUIWords
import com.aruny.fallingwords.domain.model.UIWordsModel
import com.aruny.fallingwords.domain.usecase.WordMixerUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

// TODO: Cover all the logic in this class
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class FallingWordsFragmentViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val wordMixerUseCase: WordMixerUseCase = mock()

    private lateinit var viewModel: FallingWordsFragmentViewModel

    @Before
    fun setUp() {
        viewModel = FallingWordsFragmentViewModel(wordMixerUseCase)
    }

    @Test
    fun `verify the initial value of state flows`() {
        assertThat(viewModel.livesStateFlow.value, `is`(3))
        assertThat(viewModel.scoreStateFlow.value, `is`(0))
        assertThat(viewModel.timerStateFlow.value, `is`(0))
        assertThat(viewModel.currentWordFlow.value, `is`(UIWordsModel.NOT_LOADED))
    }

    @Test
    @Ignore(
        "Fix the NPE - java.lang.NullPointerException at " +
                "com.aruny.fallingwords.domain.usecase.WordMixerUseCase-fetchWords"
    )
    // TODO: Fix
    fun `start game verify ui state and the word flow`() = runTest {
        val testList = getTestUIWords(3)
        doReturn(testList).whenever(wordMixerUseCase).fetchWords()
        viewModel.startGame()

        assertThat(viewModel.uiState.value, `is`(UiState.WordsFetched))
        assertThat(viewModel.currentWordFlow.value, `is`(testList[0]))
    }
}