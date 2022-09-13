package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model

import com.example.moviesuniverse.domain.models.MovieStaffDetail


sealed class MovieStaffDetailState {
    object Loading : MovieStaffDetailState()
    data class Success(val movieStaffDetail: MovieStaffDetail) : MovieStaffDetailState()
    data class Error(val exception: Throwable) : MovieStaffDetailState()
}