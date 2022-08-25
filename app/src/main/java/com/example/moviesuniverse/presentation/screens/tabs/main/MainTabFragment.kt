package com.example.moviesuniverse.presentation.screens.tabs.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MainFragmentBinding
import com.example.moviesuniverse.presentation.screens.tabs.main.model.MainTabState
import com.example.moviesuniverse.presentation.screens.tabs.recycler.MoviesAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainTabFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MainTabViewModel>()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mainTabState

        lifecycleScope.launchWhenStarted {
            viewModel.mainTabState.collect { state ->
                when(state) {
                    MainTabState.LoadError -> TODO()
                    is MainTabState.Loaded -> {
                        moviesAdapter.submitList(state.movies)
                    }
                    MainTabState.Loading -> {

                    }
                    MainTabState.RefreshError -> TODO()
                    is MainTabState.Refreshed -> TODO()
                    MainTabState.Refreshing -> TODO()
                }
            }
        }
        setupRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerMain.adapter = null
        _binding = null
    }

    private fun setupRecycler() {
        val recycler = binding.recyclerMain
        moviesAdapter = MoviesAdapter { id ->
            Snackbar.make(binding.root, id, Snackbar.LENGTH_SHORT).show()
        }
        recycler.adapter = moviesAdapter
    }

}