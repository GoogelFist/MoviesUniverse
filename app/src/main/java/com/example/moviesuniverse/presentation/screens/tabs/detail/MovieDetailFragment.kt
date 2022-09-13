package com.example.moviesuniverse.presentation.screens.tabs.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieDetailFragmentBinding
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER
import com.example.moviesuniverse.domain.models.MovieDetail
import com.example.moviesuniverse.presentation.screens.Screens
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

    private val movieId: String by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getString(KEY_ID) ?: ""
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
        viewModel.obtainEvent(MovieDetailEvent.OnLoadMovie(movieId))

        arguments?.getInt(TITLE_KEY)?.let { title -> binding.tvTitleDetail.setText(title) }
    }

    private fun configButtons() {
        with(binding) {
            ivBackButton.setOnClickListener { router.exit() }
            includeMovieDetailData.tvToStaffMovieDetail.setOnClickListener { router.navigateTo(Screens.movieStaff(movieId)) }
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

            bindMovieDetailData(state.movieDetail)
        }
    }

    private fun bindMovieDetailData(movieDetail: MovieDetail) {
        with(binding.includeMovieDetailData) {

            val cornerRadius = requireActivity()
                .resources.getDimension(R.dimen.movie_detail_round_corner).toInt()

            ivPosterMovieDetail.load(movieDetail.posterUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(cornerRadius.toFloat()))
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.image_error_placeholder)
            }

            checkEmptyField(
                field = tvYearMovieDetail,
                value = movieDetail.year,
                block = llYearBlock
            )
            checkEmptyField(
                field = tvCounrtiesMovieDetail,
                value = movieDetail.countries,
                block = llCountiesBlock
            )
            checkEmptyField(
                field = tvGenresMovieDetail,
                value = movieDetail.genres,
                block = llGenresBlock
            )
            checkEmptyField(
                field = tvLengthMovieDetail,
                value = movieDetail.filmLength,
                block = llLengthBlock
            )
            checkEmptyField(
                field = tvRatingMovieDetail,
                value = movieDetail.ratingKinopoisk,
                block = llRatingBlock
            )

            tvNameMovieDetail.text = movieDetail.nameRu
            tvSloganMovieDetail.text = movieDetail.slogan
            tvDescriptionMovieDetail.text = movieDetail.description
        }
    }

    private fun checkEmptyField(field: TextView, value: String, block: LinearLayoutCompat) {
        if (value.isEmpty()) {
            block.isGone = true
        } else {
            field.text = value
        }
    }

    companion object {
        private const val KEY_ID = "id"
        private const val TITLE_KEY = "title"

        fun newInstance(id: String, @StringRes title: Int): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ID, id)
                    putInt(TITLE_KEY, title)
                }
            }
        }
    }
}