package com.aruny.fallingwords.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aruny.fallingwords.R
import com.aruny.fallingwords.databinding.FragmentFallingWordsBinding

/**
 * The fragment which hosts the actual game of falling words.
 */
class FallingWordsFragment : Fragment() {
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
            findNavController().navigate(R.id.action_FallingWordsFragment_to_WelcomeFragment)
        }

        binding.buttonCorrect.setOnClickListener {
            findNavController().navigate(R.id.action_FallingWordsFragment_to_GameResultFragment)
        }

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fall_down)
        binding.textFallingWord.startAnimation(animation)

        viewModel.getWords()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}