package com.example.moviesuniverse.presentation.screens.tabs.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MoviesSearchTabFragmentBinding
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER
import com.example.moviesuniverse.presentation.screens.Screens
import com.example.moviesuniverse.presentation.screens.tabs.main.MarginItemDecorator
import com.example.moviesuniverse.presentation.screens.tabs.main.PagingMovieAdapter
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MoviesSearchTabFragment : Fragment(R.layout.movies_search_tab_fragment) {

    private var _binding: MoviesSearchTabFragmentBinding? = null
    private val binding: MoviesSearchTabFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MoviesSearchTabViewModel>()

    private val moviesAdapter: PagingMovieAdapter by lazy(LazyThreadSafetyMode.NONE) {
        PagingMovieAdapter { id ->
            router.navigateTo(Screens.detailMovie(id, R.string.movies_tab_title))
        }
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
        _binding = MoviesSearchTabFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEditText()
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
        super.onDestroyView()
        binding.recyclerSearchMovies.adapter = null
        _binding = null
    }

    private fun setupEditText() {
        binding.textInputEditTextSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                if (v.text.isNullOrBlank()) {
                    configInitialState()
                } else {
                    viewModel.setQuery(v.text.toString())
                }

                v.clearFocus()
            }
            false
        }
    }

    private fun setupRecycler() {
        binding.recyclerSearchMovies.apply {
            adapter = moviesAdapter
            addItemDecoration(marginItemDecorator)
        }

        moviesAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.NotLoading -> {
                    with(binding.textInputEditTextSearch) {
                        when {
                            isInitialState() -> configInitialState()
                            isEmptyResultState() -> configEmptyResultState()
                            else -> configLoadedState()
                        }
                    }
                }

                is LoadState.Loading -> configLoadingState()

                is LoadState.Error -> {
                    if (isEmptyAdapter()) {
                        configErrorSearchState()
                    } else {
                        configErrorState()
                    }
                }
            }
        }
    }

    private fun TextInputEditText.isInitialState() = isEmptyAdapter() && text.isNullOrBlank()

    private fun TextInputEditText.isEmptyResultState() = isEmptyAdapter() && !text.isNullOrBlank()

    private fun isEmptyAdapter() = moviesAdapter.itemCount == 0

    private fun observeViewModel() {
        viewModel.movies
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { movies -> moviesAdapter.submitData(movies) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun configInitialState() {
        with(binding) {
            llInitMessage.isVisible = true
            llEmptyResultMessage.isVisible = false
            recyclerSearchMovies.isVisible = false
            progressBarSearchMovies.isVisible = false
            errorSearchEmpty.errorBlock.isVisible = false
        }
    }

    private fun configLoadedState() {
        with(binding) {
            llInitMessage.isVisible = false
            llEmptyResultMessage.isVisible = false
            recyclerSearchMovies.isVisible = true
            progressBarSearchMovies.isVisible = false

            errorSearchEmpty.errorBlock.isVisible = false
        }
    }

    private fun configLoadingState() {
        with(binding) {
            llInitMessage.isVisible = false
            llEmptyResultMessage.isVisible = false
            recyclerSearchMovies.isVisible = false
            progressBarSearchMovies.isVisible = true

            errorSearchEmpty.errorBlock.isVisible = false
        }
    }

    private fun configErrorState() {
        showErrorSnackBar()

        configLoadedState()
    }

    private fun configErrorSearchState() {
        with(binding) {
            llInitMessage.isVisible = false
            llEmptyResultMessage.isVisible = false
            recyclerSearchMovies.isVisible = false
            progressBarSearchMovies.isVisible = false

            errorSearchEmpty.errorBlock.isVisible = true
        }
    }

    private fun configEmptyResultState() {
        with(binding) {
            llInitMessage.isVisible = false
            llEmptyResultMessage.isVisible = true
            recyclerSearchMovies.isVisible = false
            progressBarSearchMovies.isVisible = false

            errorSearchEmpty.errorBlock.isVisible = false
        }
    }

    private fun showErrorSnackBar() {
        val bottomBar: BottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation_view)

        Snackbar
            .make(binding.root, R.string.snack_bar_error_text, Snackbar.LENGTH_LONG)
            .setAnchorView(bottomBar)
            .show()
    }

    private fun setupButtons() {
        binding.errorSearchEmpty.buttonTryAgain.setOnClickListener {
            moviesAdapter.retry()
        }
    }

    companion object {
        private const val SPAN_COUNT = 2
    }
}