package com.example.androidtrivia

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.androidtrivia.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    private lateinit var binding : FragmentGameWonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view : View->
            view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getShareIntent() : Intent{
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT,getString(R.string.share_success_text,args.numQuestions,args.numCorrect))
        return shareIntent
    }

    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu,menu)
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)){
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

}