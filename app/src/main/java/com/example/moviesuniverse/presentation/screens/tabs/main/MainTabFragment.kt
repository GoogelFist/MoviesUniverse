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
import com.example.moviesuniverse.presentation.screens.tabs.main.model.MainTabEvent
import com.example.moviesuniverse.presentation.screens.tabs.main.model.MainTabState
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        PagingMovieAdapter { id -> viewModel.obtainEvent(MainTabEvent.OnNavToDetailScreen(id)) }
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
        setupButtons()
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
        binding.recyclerMain.apply {
            adapter = moviesAdapter
            addItemDecoration(marginItemDecorator)
        }

        moviesAdapter.addLoadStateListener { state ->
            if (state.refresh is LoadState.Loading) {
                viewModel.obtainEvent(MainTabEvent.OnLoading)
            } else {
                val errorState = when {
                    state.append is LoadState.Error -> state.append as LoadState.Error
                    state.prepend is LoadState.Error -> state.prepend as LoadState.Error
                    state.refresh is LoadState.Error -> state.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    if (isEmptyAdapter()) {
                        viewModel.obtainEvent(MainTabEvent.OnEmpty)
                    } else {
                        viewModel.obtainEvent(MainTabEvent.OnRefreshError)
                    }
                }
            }
        }
    }

    private fun isEmptyAdapter() = moviesAdapter.itemCount == 0

    private fun configSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.obtainEvent(MainTabEvent.OnRefresh)
                moviesAdapter.refresh()
                viewModel.obtainEvent(MainTabEvent.OnDefault)
            }
        }
    }

    private fun setupButtons() {
        binding.buttonTryAgain.setOnClickListener {
            moviesAdapter.retry()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { state ->
                    when (state) {
                        MainTabState.Loading -> configLoadingState()
                        is MainTabState.Loaded -> configLoadedState(state)
                        MainTabState.Empty -> configEmptyState()
                        MainTabState.RefreshError -> configRefreshErrorState()
                        MainTabState.Refreshing -> configRefreshingState()
                        MainTabState.Default -> configDefaultState()
                        is MainTabState.NavToDetailScreen -> configNavToDetailScreenState(state)
                    }
                }
        }
    }

    private fun configLoadingState() {
        with(binding) {
            progressBarMainScreen.isVisible = true
            recyclerMain.isVisible = false

            swipeRefreshLayout.isVisible = false

            llMainErrorBlock.isVisible = false
        }
    }

    private fun configLoadedState(state: MainTabState.Loaded) {
        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true

            llMainErrorBlock.isVisible = false
        }

        moviesAdapter.submitData(viewLifecycleOwner.lifecycle, state.movies)
    }

    private fun configEmptyState() {
        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = false

            swipeRefreshLayout.isVisible = false

            llMainErrorBlock.isVisible = true
        }
    }

    private fun configRefreshErrorState() {
        showErrorSnackBar()

        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = false

            llMainErrorBlock.isVisible = false
        }
    }

    private fun configRefreshingState() {
        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = true

            llMainErrorBlock.isVisible = false
        }
    }

    private fun configDefaultState() {
        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = false

            llMainErrorBlock.isVisible = false
        }
    }

    private fun configNavToDetailScreenState(state: MainTabState.NavToDetailScreen) {
        router.navigateTo(Screens.detailMovie(state.id))
        viewModel.obtainEvent(MainTabEvent.OnDefault)
    }

    private fun showErrorSnackBar() {
        val bottomBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation_view)
        Snackbar
            .make(binding.root, R.string.snack_bar_error_text, Snackbar.LENGTH_LONG)
            .setAnchorView(bottomBar)
            .show()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}