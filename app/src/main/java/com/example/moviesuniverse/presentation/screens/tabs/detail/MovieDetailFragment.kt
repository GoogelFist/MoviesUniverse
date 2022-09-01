package com.example.moviesuniverse.presentation.screens.tabs.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviesuniverse.R
import com.example.moviesuniverse.databinding.MovieDetailFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment) {

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding: MovieDetailFragmentBinding
        get() = _binding!!

    private val viewModel by viewModel<MovieDetailViewModel>()

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorSnackBar(isNeed: Boolean) {
        if (isNeed) {
            Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeViewModel() {
        val id = arguments?.getString(KEY_ID)

        viewLifecycleOwner.lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                id?.let { movieId ->
                    viewModel.getMovieDetail(movieId)
                }

                viewModel.movieDetailState.collect { state ->
                    when (state) {
                        MovieDetailUiState.Loading -> {}
                        is MovieDetailUiState.Error -> {}
                        is MovieDetailUiState.Success -> {
                            binding.tvMovieDetailTitle.text = state.movieDetail.nameRu
                        }
                    }
                }
            }
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