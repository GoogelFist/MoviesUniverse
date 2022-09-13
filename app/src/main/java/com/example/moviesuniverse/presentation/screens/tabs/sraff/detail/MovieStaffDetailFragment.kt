package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.example.moviesuniverse.databinding.MovieStaffDetailFragmentBinding
import com.example.moviesuniverse.di.GLOBAL_QUALIFIER
import com.example.moviesuniverse.domain.models.MovieStaffDetail
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model.MovieStaffDetailEvent
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model.MovieStaffDetailState
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MovieStaffDetailFragment : Fragment(R.layout.movie_staff_detail_fragment) {

    private var _binding: MovieStaffDetailFragmentBinding? = null
    private val binding: MovieStaffDetailFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MovieStaffDetailViewModel>()

    private val router: Router by inject(qualifier = named(GLOBAL_QUALIFIER))
    private val navigatorHolder: NavigatorHolder by inject(qualifier = named(GLOBAL_QUALIFIER))
    private val navigator: AppNavigator by lazy(LazyThreadSafetyMode.NONE) {
        AppNavigator(
            requireActivity(),
            R.id.fragment_container,
            requireActivity().supportFragmentManager
        )
    }

    private val staffId: String by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getString(KEY_ID) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieStaffDetailFragmentBinding.inflate(inflater, container, false)
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
        viewModel.movieStaffDetailState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when (state) {
                    MovieStaffDetailState.Loading -> setLoadingState()
                    is MovieStaffDetailState.Error -> setErrorState()
                    is MovieStaffDetailState.Success -> setSuccessState(state)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun init() {
        viewModel.obtainEvent(MovieStaffDetailEvent.OnLoadMovieStaff(staffId))
    }

    private fun configButtons() {
        with(binding) {
            ivBackButton.setOnClickListener { router.exit() }
        }
    }

    private fun setLoadingState() {
        with(binding) {
            progressBarMovieStaffDetail.isVisible = true
            groupSuccessDetailMovie.isVisible = false
            groupErrorDetailMovie.isVisible = false
        }
    }

    private fun setErrorState() {
        with(binding) {
            progressBarMovieStaffDetail.isVisible = false
            groupSuccessDetailMovie.isVisible = false
            groupErrorDetailMovie.isVisible = true
        }
    }

    private fun setSuccessState(state: MovieStaffDetailState.Success) {
        with(binding) {
            progressBarMovieStaffDetail.isVisible = false
            groupSuccessDetailMovie.isVisible = true
            groupErrorDetailMovie.isVisible = false

            bindMovieDetailData(state.movieStaffDetail)
        }
    }

    private fun bindMovieDetailData(movieStaffDetail: MovieStaffDetail) {
        with(binding.includeMovieDetailData) {

            val cornerRadius = requireActivity()
                .resources.getDimension(R.dimen.movie_detail_round_corner).toInt()

            ivPosterMovieStaffDetail.load(movieStaffDetail.posterUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(cornerRadius.toFloat()))
                placeholder(R.drawable.image_placeholder)
                error(R.drawable.image_error_placeholder)
            }

            checkEmptyField(
                field = tvBirthplaceMovieStaffDetail,
                value = movieStaffDetail.birthDay,
                block = llBirthdayBlock
            )
            checkEmptyField(
                field = tvBirthplaceMovieStaffDetail,
                value = movieStaffDetail.birthPlace,
                block = llBirthplaceBlock
            )

            tvNameMovieStaffDetail.text = movieStaffDetail.name
            tvFactsMovieStaffDetail.text = movieStaffDetail.facts
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

        fun newInstance(staffId: String): MovieStaffDetailFragment {
            return MovieStaffDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ID, staffId)
                }
            }
        }
    }
}