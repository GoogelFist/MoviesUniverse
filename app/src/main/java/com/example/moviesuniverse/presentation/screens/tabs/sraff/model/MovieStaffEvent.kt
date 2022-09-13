package com.example.moviesuniverse.presentation.screens.tabs.sraff.model

sealed class MovieStaffEvent {
    data class OnLoadMovieStaff(val movieId: String): MovieStaffEvent()
}