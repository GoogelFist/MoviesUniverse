package com.example.moviesuniverse.presentation.screens.tabs.main.model

import com.example.moviesuniverse.domain.models.MovieItem

sealed class MainTabState {
    object Loading: MainTabState()
    data class Loaded(val movies: List<MovieItem>) : MainTabState()
    object LoadError: MainTabState()

    object Refreshing: MainTabState()
    data class Refreshed(val movies: List<MovieItem>) : MainTabState()
    object RefreshError: MainTabState()
}
