package com.example.moviesuniverse.presentation.screens.tabs.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MainFragmentBinding
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER
import com.example.moviesuniverse.presentation.screens.Screens
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainTabFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MainTabViewModel>()

    private val moviesAdapter: PagingMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingMovieAdapter { id -> router.navigateTo(Screens.detailMovie(id)) }
    }

    private val marginItemDecorator: MarginItemDecorator by lazy(LazyThreadSafetyMode.NONE) {
        MarginItemDecorator(
            verticalMargin = R.dimen.item_decorator_vertical_margin,
            horizontalMargin = R.dimen.item_decorator_horizontal_margin,
            spanCount = SPAN_COUNT
        )
    }

    private val router: Router by inject(qualifier = named(GLOBAL_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(qualifier = named(GLOBAL_QUALIFIER))
    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        AppNavigator(
            requireActivity(),
            R.id.fragment_container,
            requireActivity().supportFragmentManager
        )
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

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerMain.adapter = null
        _binding = null
    }

    private fun setupRecycler() {
        val recycler = binding.recyclerMain
        recycler.adapter = moviesAdapter

        moviesAdapter.addLoadStateListener { state ->
            with(binding) {
                recycler.isVisible = state.refresh != LoadState.Loading
                progressBarMainScreen.isVisible = state.refresh == LoadState.Loading

                showErrorSnackBar(state.refresh is LoadState.Error)
            }
        }

        recycler.addItemDecoration(marginItemDecorator)

    }

    private fun showErrorSnackBar(isNeed: Boolean) {
        if (isNeed) {
            Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun configSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                isRefreshing = false
                moviesAdapter.refresh()
                isRefreshing = false
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getMovieList()
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { movies ->
                    moviesAdapter.submitData(viewLifecycleOwner.lifecycle, movies)
                }
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}