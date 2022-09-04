package com.example.moviesuniverse.presentation.screens.tabs.detail.model

sealed class MovieDetailEvent {
    data class OnLoadMovie(val id: String): MovieDetailEvent()
}