package com.example.moviesuniverse.presentation.screens.tabs.detail.model

import com.example.moviesuniverse.domain.models.MovieDetail


sealed class MovieDetailState {
    object Loading : MovieDetailState()
    data class Success(val movieDetail: MovieDetail) : MovieDetailState()
    data class Error(val exception: Throwable) : MovieDetailState()
}