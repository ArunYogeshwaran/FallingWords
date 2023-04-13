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
import com.aruny.fallingwords.data.WordTranslationPair
import com.aruny.fallingwords.databinding.FragmentFallingWordsBinding

/**
 * The fragment which hosts the actual game of falling words.
 */
class FallingWordsFragment : Fragment() {
    private var listOfWords: List<WordTranslationPair> = mutableListOf()
    private var currentWordIndex = 0
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

        binding.buttonWrong.setOnClickListener {
//            findNavController().navigate(R.id.action_FallingWordsFragment_to_WelcomeFragment)
        }

        binding.buttonCorrect.setOnClickListener {
//            findNavController().navigate(R.id.action_FallingWordsFragment_to_GameResultFragment)
        }

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_fall_down)
        viewModel.wordPairLiveData.observe(viewLifecycleOwner) {
            listOfWords = it
            binding.textFallingWord.text = listOfWords[currentWordIndex].textEng
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    binding.textEnglishWord.text = listOfWords[currentWordIndex].textEng
                    binding.textFallingWord.text = listOfWords[currentWordIndex].textSpa
                    binding.textFallingWord.startAnimation(animation)
                }

                override fun onAnimationEnd(animation: Animation?) {
                    currentWordIndex++
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
        viewModel.getWords()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}