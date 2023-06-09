package com.aruny.fallingwords.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aruny.fallingwords.R
import com.aruny.fallingwords.databinding.FragmentGameResultBinding

/**
 * The fragment which shows the result of the game.
 */
class GameResultFragment : Fragment() {

    private var _binding: FragmentGameResultBinding? = null

    private val args: GameResultFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textFinalScore.text = getString(R.string.your_score_is, args.score)
        binding.buttonEndGame.setOnClickListener {
            findNavController().navigate(R.id.action_GameResultFragment_to_WelcomeFragment)
        }

        binding.buttonRestartGame.setOnClickListener {
            findNavController().navigate(R.id.action_GameResultFragment_to_FallingWordsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}