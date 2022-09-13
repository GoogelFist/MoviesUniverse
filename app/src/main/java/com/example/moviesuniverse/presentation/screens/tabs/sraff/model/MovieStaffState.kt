package com.example.moviesuniverse.presentation.screens.tabs.sraff.model

import com.example.moviesuniverse.domain.models.MovieStaffItem

sealed class MovieStaffState {
    object Loading : MovieStaffState()
    data class Success(val movieStaff: List<MovieStaffItem>) : MovieStaffState()
    data class Error(val exception: Throwable) : MovieStaffState()
}