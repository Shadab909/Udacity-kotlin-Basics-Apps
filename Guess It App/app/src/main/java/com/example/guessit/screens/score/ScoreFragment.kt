package com.example.guessit.screens.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.guessit.R
import com.example.guessit.databinding.FragmentScoreBinding


class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentScoreBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_score,
            container,
            false
        )



        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = viewModel

//        viewModel.currentScore.observe(viewLifecycleOwner){ newScore->
//            binding.scoreText.text = newScore.toString()
//        }

        viewModel.eventPlayAgain.observe(viewLifecycleOwner){ playAgain->
            if (playAgain){
                findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
                viewModel.onPlayAgainComplete()
            }
        }


//        binding.playAgainButton.setOnClickListener { onPlayAgain() }

        return binding.root
    }


}