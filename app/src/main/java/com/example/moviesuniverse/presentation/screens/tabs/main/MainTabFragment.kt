package com.example.moviesuniverse.presentation.screens.tabs.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainTabFragment : Fragment(R.layout.main_fragment) {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MainTabViewModel>()

    private val moviesAdapter: PagingMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingMovieAdapter { id -> router.navigateTo(Screens.detailMovie(id, R.string.main_tab_title)) }
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
        binding.recyclerMain.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun setupRecycler() {
        binding.recyclerMain.apply {
            adapter = moviesAdapter
            addItemDecoration(marginItemDecorator)
        }

        moviesAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.NotLoading -> {
                    configDefaultState()
                }

                is LoadState.Loading -> {
                    if (isEmptyAdapter()) {
                        configLoadingState()
                    } else {
                        configRefreshingState()
                    }
                }

                is LoadState.Error -> {
                    if (isEmptyAdapter()) {
                        configEmptyState()
                    } else {
                        configRefreshErrorState()
                    }
                }
            }
        }
    }

    private fun isEmptyAdapter() = moviesAdapter.itemCount == 0

    private fun setupButtons() {
        binding.errorEmpty.buttonTryAgain.setOnClickListener {
            moviesAdapter.retry()
        }
    }

    private fun observeViewModel() {
        viewModel.getMovieList()
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { movies ->
                moviesAdapter.submitData(movies)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun configDefaultState() {

        configSwipeRefresh { moviesAdapter.refresh() }

        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = false

            errorEmpty.errorBlock.isVisible = false
        }
    }

    private fun configLoadingState() {
        with(binding) {
            progressBarMainScreen.isVisible = true
            recyclerMain.isVisible = false

            swipeRefreshLayout.isVisible = false

            errorEmpty.errorBlock.isVisible = false
        }
    }

    private fun configEmptyState() {
        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = false

            swipeRefreshLayout.isVisible = false

            errorEmpty.errorBlock.isVisible = true
        }
    }

    private fun configRefreshingState() {
        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = true

            errorEmpty.errorBlock.isVisible = false
        }
    }

    private fun configRefreshErrorState() {
        showErrorSnackBar(R.string.snack_bar_error_text)

        configSwipeRefresh { moviesAdapter.retry() }

        with(binding) {
            progressBarMainScreen.isVisible = false
            recyclerMain.isVisible = true

            swipeRefreshLayout.isVisible = true
            swipeRefreshLayout.isRefreshing = false

            errorEmpty.errorBlock.isVisible = false
        }
    }

    private fun configSwipeRefresh(action: () -> Unit) {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                action.invoke()
            }
        }
    }

    private fun showErrorSnackBar(@StringRes errorMessage: Int) {
        val bottomBar: BottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation_view)

        Snackbar
            .make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
            .setAnchorView(bottomBar)
            .show()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}