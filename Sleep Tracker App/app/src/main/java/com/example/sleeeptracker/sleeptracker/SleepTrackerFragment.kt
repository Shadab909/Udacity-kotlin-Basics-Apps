package com.example.sleeeptracker.sleeptracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sleeeptracker.R
import com.example.sleeeptracker.database.SleepDatabase
import com.example.sleeeptracker.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar


class SleepTrackerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sleep_tracker, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(SleepTrackerViewModel::class.java)

        binding.sleepTrackerViewModel = viewModel
        binding.lifecycleOwner = this

        val manager = GridLayoutManager(activity,3)
        binding.sleepList.layoutManager = manager
        val adapter = SleepNightAdapter(SleepNightAdapter.SleepNightListener { nightId->
            viewModel.onSleepNightClicked(nightId)
        })
        binding.sleepList.adapter = adapter
        viewModel.nights.observe(viewLifecycleOwner){
            it?.let{
                adapter.submitList(it)
            }
        }

        viewModel.navigateToSleepQuality.observe(viewLifecycleOwner) { night ->
            night?.let {
                this.findNavController().navigate(
                    SleepTrackerFragmentDirections
                        .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId)
                )
                viewModel.doneNavigating()
            }
        }

        viewModel.navigateToSleepDataQuality.observe(viewLifecycleOwner, Observer {night ->
            night?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections
                    .actionSleepTrackerFragmentToSleepDetailFragment(night))
                viewModel.onSleepDataQualityNavigated()
            }
        })

//        viewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
//            if (it == true) { // Observed state is true.
//                Snackbar.make(
//                    requireActivity().findViewById(android.R.id.content),
//                    getString(R.string.cleared_message),
//                    Snackbar.LENGTH_SHORT // How long to display the message.
//                ).show()
//                viewModel.doneShowingSnackbar()
//            }
//        })


        return binding.root
    }
}

