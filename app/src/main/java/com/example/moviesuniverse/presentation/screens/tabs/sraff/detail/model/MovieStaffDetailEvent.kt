package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model

sealed class MovieStaffDetailEvent {
    data class OnLoadMovieStaff(val id: String): MovieStaffDetailEvent()
}