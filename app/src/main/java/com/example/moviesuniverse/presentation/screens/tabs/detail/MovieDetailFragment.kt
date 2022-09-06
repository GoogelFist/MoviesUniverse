package com.example.moviesuniverse.presentation.screens.tabs.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieDetailFragmentBinding
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailEvent
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailState
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment) {

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding: MovieDetailFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MovieDetailViewModel>()

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
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        init()
        configButtons()
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
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.movieDetailState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when (state) {
                    MovieDetailState.Loading -> setLoadingState()
                    is MovieDetailState.Error -> setErrorState()
                    is MovieDetailState.Success -> setSuccessState(state)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun init() {
        val id = arguments?.getString(KEY_ID) ?: ""
        viewModel.obtainEvent(MovieDetailEvent.OnLoadMovie(id))
    }

    private fun configButtons() {
        binding.ivBackButton.setOnClickListener {
            router.exit()
        }
    }

    private fun setLoadingState() {
        with(binding) {
            progressBarMovieDetail.isVisible = true
            groupSuccessDetailMovie.isVisible = false
            groupErrorDetailMovie.isVisible = false
        }
    }

    private fun setErrorState() {
        with(binding) {
            progressBarMovieDetail.isVisible = false
            groupSuccessDetailMovie.isVisible = false
            groupErrorDetailMovie.isVisible = true
        }
    }

    private fun setSuccessState(state: MovieDetailState.Success) {
        with(binding) {
            progressBarMovieDetail.isVisible = false
            groupSuccessDetailMovie.isVisible = true
            groupErrorDetailMovie.isVisible = false

            bindMovieDetailData(state)
        }
    }

    private fun bindMovieDetailData(state: MovieDetailState.Success) {
        with(binding.includeMovieDetailData) {
            ivPosterMovieDetail.load(state.movieDetail.posterUrl) {
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.image_error_placeholder)
                allowHardware(false)
            }

            tvNameMovieDetail.text = state.movieDetail.nameRu
            tvYearMovieDetail.text = state.movieDetail.year
            tvCountiesMovieDetail.text = state.movieDetail.countries
            tvSloganMovieDetail.text = state.movieDetail.slogan
            tvGenresMovieDetail.text = state.movieDetail.genres
            tvLengthMovieDetail.text = state.movieDetail.filmLength
            tvRatingMovieDetail.text = state.movieDetail.ratingKinopoisk
            tvDescriptionMovieDetail.text = state.movieDetail.description
        }
    }

    companion object {
        private const val KEY_ID = "id"

        fun newInstance(id: String): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ID, id)
                }
            }
        }
    }
}