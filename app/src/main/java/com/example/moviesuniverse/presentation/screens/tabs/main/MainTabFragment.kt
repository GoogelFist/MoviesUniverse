package com.example.moviesuniverse.presentation.screens.tabs.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MainFragmentBinding
import com.example.moviesuniverse.presentation.screens.tabs.PagingMovieAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainTabFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MainTabViewModel>()

    private lateinit var moviesAdapter: PagingMovieAdapter

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
            viewModel.getMovieList().observe(viewLifecycleOwner) {
                it?.let {
                    moviesAdapter.submitData(lifecycle, it)
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

        moviesAdapter = PagingMovieAdapter { id ->
            Snackbar.make(binding.root, id, Snackbar.LENGTH_SHORT).show()
        }
        recycler.adapter = moviesAdapter

        moviesAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                binding.progressBarMainScreen.isVisible = true
            else {
                binding.progressBarMainScreen.isVisible = false
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }
    }

}