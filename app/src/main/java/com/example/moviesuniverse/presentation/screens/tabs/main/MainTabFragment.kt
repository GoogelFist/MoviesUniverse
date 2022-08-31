package com.example.moviesuniverse.presentation.screens.tabs.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainTabFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MainTabViewModel>()

    private val moviesAdapter: PagingMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingMovieAdapter { id -> Snackbar.make(binding.root, id, Snackbar.LENGTH_LONG).show() }
    }

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

        setupRecycler()
        configSwipeRefresh()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerMain.adapter = null
        _binding = null
    }

    private fun setupRecycler() {
        val recycler = binding.recyclerMain
        recycler.adapter = moviesAdapter

//        recycler.adapter = moviesAdapter.withLoadStateHeaderAndFooter(
//            footer = MoviesLoaderStateAdapter(),
//            header = MoviesLoaderStateAdapter()
//        )

        moviesAdapter.addLoadStateListener { state ->
            with(binding) {
                recycler.isVisible = state.refresh != LoadState.Loading
                progressBarMainScreen.isVisible = state.refresh == LoadState.Loading

                val errorState = when {
                    state.append is LoadState.Error -> state.append as LoadState.Error
                    state.prepend is LoadState.Error ->  state.prepend as LoadState.Error
                    state.refresh is LoadState.Error -> state.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Snackbar.make(binding.root, it.error.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun configSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            isRefreshing = true

            setOnRefreshListener {
                moviesAdapter.refresh()
            }

            isRefreshing = false
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getMovieList().collectLatest { movies ->
                moviesAdapter.submitData(movies)
            }
        }
    }
}