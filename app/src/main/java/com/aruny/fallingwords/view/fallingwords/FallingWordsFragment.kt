package com.aruny.fallingwords.view.fallingwords

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.aruny.fallingwords.R
import com.aruny.fallingwords.databinding.FragmentFallingWordsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * The fragment which hosts the actual game of falling words.
 */
@AndroidEntryPoint
class FallingWordsFragment : Fragment() {
    private var _binding: FragmentFallingWordsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FallingWordsFragmentViewModel by viewModels()

    // Boolean to track whether the user answered before the word fell down
    private var responseReceived: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFallingWordsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        observeUiState()
        viewModel.startGame()
    }

    private fun setClickListeners() {
        binding.buttonCorrect.setOnClickListener {
            responseReceived = true
            viewModel.checkAnswer(true)
        }

        binding.buttonWrong.setOnClickListener {
            responseReceived = true
            viewModel.checkAnswer(false)
        }
    }

    private fun collectFlows() {
        with(binding) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.currentWordFlow.collectLatest {
                            // TODO: Remove the Ans: here
                            textEnglishWord.text =
                                it.englishWord + " --> is Correct : " + it.isCorrectTranslation
                            textFallingWord.text = it.otherLangWord
                        }
                    }
                    launch {
                        viewModel.scoreStateFlow.collectLatest {
                            textCurrentScore.text = getString(R.string.score_number, it)
                        }
                    }
                    launch {
                        viewModel.livesStateFlow.collectLatest {
                            textCurrentLives.text = getString(R.string.lives_number, it)
                        }
                    }
                    launch {
                        viewModel.timerStateFlow.collectLatest {
                            textTimer.text = getString(R.string.timer_secs, it)
                        }
                    }
                }
            }
        }
    }

    private fun animateWord(durationMilliSecs: Long) {
        with(binding) {
            val transitionValue = textEnglishWord.top - textFallingWord.bottom
            textFallingWord.translationY = DEFAULT_TRANSLATION_Y
            textFallingWord
                .animate()
                .translationY(transitionValue.toFloat())
                .setDuration(durationMilliSecs)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        if (responseReceived.not()) {
                            viewModel.handleNoResponseFromUser()
                        }
                        responseReceived = false
                    }
                })
        }
    }

    private fun FragmentFallingWordsBinding.toggleButtons(shouldEnable: Boolean) {
        buttonCorrect.isEnabled = shouldEnable
        buttonWrong.isEnabled = shouldEnable
    }

    private fun observeUiState() {
        collectFlows()
        viewModel.uiState.observe(viewLifecycleOwner) {
            with(binding) {
                when (it) {
                    is UiState.FetchingWords -> {
                        progressBar.visibility = View.VISIBLE
                        binding.textCurrentScore.text = getString(R.string.score_number, it.score)
                        binding.textCurrentLives.text = getString(R.string.lives_number, it.lives)
                    }
                    is UiState.WordsFetched -> {
                        progressBar.visibility = View.GONE
                        viewModel.getNextWord()
                    }
                    is UiState.NextWord -> {
                        animateWord(it.durationMilliSecs)
                    }
                    is UiState.CorrectAnswer -> {
                        updateCurrentScore(it.score)
                    }
                    is UiState.IncorrectAnswer -> {
                        updateCurrentLives(it.lives)
                    }
                    is UiState.GameOver -> {
                        textFallingWord.animate().cancel()
                        val options = NavOptions.Builder().setPopUpTo(
                            R.id.fallingWordsFragment, true
                        ).build()
                        findNavController().navigate(
                            FallingWordsFragmentDirections.actionFallingWordsFragmentToGameResultFragment(
                                it.score.toString()
                            ), options
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // TODO: Should pause the animation and the timer here
    }

    override fun onResume() {
        super.onResume()
        // TODO: Should resume the animation and the timer here
    }

    private fun updateCurrentScore(score: Int) {
        binding.textCurrentScore.text = getString(R.string.score_number, score)
    }

    private fun updateCurrentLives(lives: Int) {
        binding.textCurrentLives.text = getString(R.string.lives_number, lives)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.textFallingWord.animate().setListener(null)
        _binding = null
    }

    companion object {
        private const val DEFAULT_TRANSLATION_Y = 0f
    }
}