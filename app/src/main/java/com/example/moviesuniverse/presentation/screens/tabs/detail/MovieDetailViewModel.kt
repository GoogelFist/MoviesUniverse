package com.example.moviesuniverse.presentation.screens.tabs.detail

import androidx.lifecycle.ViewModel
import com.example.moviesuniverse.domain.models.MovieDetail
import com.example.moviesuniverse.domain.usecases.LoadDetailMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MovieDetailViewModel(private val loadDetailMovieUseCase: LoadDetailMovieUseCase) :
    ViewModel() {

    private val _movieDetailState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val movieDetailState: StateFlow<MovieDetailUiState> = _movieDetailState

    suspend fun getMovieDetail(id: String) {
        _movieDetailState.update { MovieDetailUiState.Loading }
        val movieDetail = loadDetailMovieUseCase(id)
        _movieDetailState.update { MovieDetailUiState.Success(movieDetail) }
    }
}

sealed class MovieDetailUiState {
    object Loading : MovieDetailUiState()
    data class Success(val movieDetail: MovieDetail) : MovieDetailUiState()
    data class Error(val exception: Throwable) : MovieDetailUiState()
}