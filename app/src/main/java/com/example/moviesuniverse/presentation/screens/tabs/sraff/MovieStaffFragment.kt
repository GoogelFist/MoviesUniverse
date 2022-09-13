package com.example.moviesuniverse.presentation.screens.tabs.sraff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieStaffFragmentBinding
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER
import com.example.moviesuniverse.presentation.screens.Screens
import com.example.moviesuniverse.presentation.screens.tabs.sraff.model.MovieStaffEvent
import com.example.moviesuniverse.presentation.screens.tabs.sraff.model.MovieStaffState
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MovieStaffFragment : Fragment(R.layout.movie_staff_fragment) {

    private var _binding: MovieStaffFragmentBinding? = null
    private val binding: MovieStaffFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MovieStaffViewModel>()

    private val movieStaffAdapter: MovieStaffAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MovieStaffAdapter { staffId -> router.navigateTo(Screens.detailStaff(staffId)) }
    }

    private val movieId: String by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getString(KEY_ID) ?: ""
    }

    private val dividerItemDecoration: MaterialDividerItemDecoration by lazy(LazyThreadSafetyMode.NONE) {
        MaterialDividerItemDecoration(requireContext(), MaterialDividerItemDecoration.VERTICAL)
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
        _binding = MovieStaffFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.obtainEvent(MovieStaffEvent.OnLoadMovieStaff(movieId))
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
        binding.recyclerMovieStaff.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun setupRecycler() {
        binding.recyclerMovieStaff.apply {
            adapter = movieStaffAdapter

            dividerItemDecoration.apply {
                setDividerColorResource(requireContext(), R.color.transparent)
                setDividerThicknessResource(
                    requireContext(),
                    R.dimen.item_decorator_vertical_margin
                )
            }

            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun observeViewModel() {
        viewModel.moveStaffState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when (state) {
                    MovieStaffState.Loading -> setupLoadingState()
                    is MovieStaffState.Error -> setupErrorState()
                    is MovieStaffState.Success -> setupSuccessState(state)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupLoadingState() {
        with(binding) {
            errorEmpty.errorBlock.isVisible = false
            recyclerMovieStaff.isVisible = false
            progressBarMovieStaff.isVisible = true
        }
    }

    private fun setupErrorState() {
        with(binding) {
            errorEmpty.errorBlock.isVisible = true
            recyclerMovieStaff.isVisible = false
            progressBarMovieStaff.isVisible = false
        }
    }

    private fun setupSuccessState(state: MovieStaffState.Success) {
        with(binding) {
            errorEmpty.errorBlock.isVisible = false
            recyclerMovieStaff.isVisible = true
            progressBarMovieStaff.isVisible = false
        }

        movieStaffAdapter.submitList(state.movieStaff)
    }

    private fun setupButtons() {
        with(binding) {
            ivMovieStaffBackButton.setOnClickListener {
                router.exit()
            }
            errorEmpty.buttonTryAgain.setOnClickListener {
                viewModel.obtainEvent(MovieStaffEvent.OnLoadMovieStaff(movieId))
            }
        }
    }

    companion object {
        private const val KEY_ID = "id"

        fun newInstance(id: String): MovieStaffFragment {
            return MovieStaffFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ID, id)
                }
            }
        }
    }
}