package com.example.guessit.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.guessit.R
import com.example.guessit.databinding.FragmentGameBinding


class GameFragment : Fragment() {


    private lateinit var binding: FragmentGameBinding

    private lateinit var viewModel: GameViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this


//        binding.correctButton.setOnClickListener {
//            viewModel.onCorrect()
//        }
//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//        }

//        viewModel.score.observe(viewLifecycleOwner) { newScore ->
//            binding.scoreText.text = newScore.toString()
//        }

//        viewModel.word.observe(viewLifecycleOwner){ newWord->
//            binding.wordText.text = newWord
//        }

        viewModel.eventGameFinished.observe(viewLifecycleOwner){ hasFinished->
            if (hasFinished){
                gameFinished()
                viewModel.gameFinishedComplete()
            }
        }

//        viewModel.currentTime.observe(viewLifecycleOwner){ currentTime->
//            val time = DateUtils.formatElapsedTime(currentTime)
//            binding.timerText.text = DateUtils.formatElapsedTime(currentTime)
//        }
        return binding.root
    }



    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(viewModel.score.value?:0)
        findNavController().navigate(action)

    }
}