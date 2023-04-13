package com.aruny.fallingwords.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aruny.fallingwords.R
import com.aruny.fallingwords.databinding.FragmentFallingWordsBinding
import com.aruny.fallingwords.domain.UIWordsModel

/**
 * The fragment which hosts the actual game of falling words.
 */
class FallingWordsFragment : Fragment() {
    private var listOfWords: List<UIWordsModel> = mutableListOf()
    private var currentWordIndex = 0
    private var currentLives = MAX_LIVES
    private var currentScore = 0
    private var _binding: FragmentFallingWordsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FallingWordsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFallingWordsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textCurrentScore.text = getString(R.string.score_number, 0)
        binding.textCurrentLives.text = getString(R.string.lives_number, MAX_LIVES)

        binding.buttonWrong.setOnClickListener {
            val currentWord = listOfWords[currentWordIndex]
            if (binding.textFallingWord.text != currentWord.correctTranslation) {
                updateCurrentScore()
                println("You are right bro!!")
            } else {
                updateCurrentLives()
                println("You are wrong bro!!")
            }
            currentWordIndex++
            startFallingWords()
        }

        binding.buttonCorrect.setOnClickListener {
            val currentWord = listOfWords[currentWordIndex]
            if (binding.textFallingWord.text == currentWord.correctTranslation) {
                updateCurrentScore()
                println("You are right bro!!")
            } else {
                updateCurrentLives()
                println("You are wrong bro!!")
            }
            currentWordIndex++
            startFallingWords()
        }

        viewModel.wordPairLiveData.observe(viewLifecycleOwner) {
            listOfWords = it
            binding.textFallingWord.text = listOfWords[currentWordIndex].optionWords.random()
            startFallingWords()
        }

        viewModel.getWords()
    }

    private fun updateCurrentScore() {
        binding.textCurrentScore.text = getString(R.string.score_number, ++currentScore)
    }

    private fun updateCurrentLives() {
        if (--currentLives == 0) {
            findNavController().navigate(R.id.action_FallingWordsFragment_to_GameResultFragment)
        } else {
            binding.textCurrentLives.text = getString(R.string.lives_number, currentLives)
        }
    }

    private fun startFallingWords() {
        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_fall_down)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // TODO: Remove the Ans: here
                binding.textEnglishWord.text =
                    listOfWords[currentWordIndex].englishWord + " (Ans:" + listOfWords[currentWordIndex].correctTranslation + ")"
                binding.textFallingWord.text =
                    listOfWords[currentWordIndex].optionWords.random()
                binding.textFallingWord.startAnimation(animation)
            }

            override fun onAnimationEnd(animation: Animation?) {
                updateCurrentLives()
                currentWordIndex++
                // Ensure we move to the result fragment when all the words are done
                if (currentWordIndex < listOfWords.size) {
                    binding.textFallingWord.startAnimation(animation)
                } else {
                    findNavController().navigate(
                        R.id.action_FallingWordsFragment_to_GameResultFragment
                    )
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        binding.textFallingWord.startAnimation(animation)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MAX_LIVES = 3
    }
}