package com.example.moviesuniverse.presentation.screens.tabs.main.model

import androidx.paging.PagingData
import com.example.moviesuniverse.domain.models.MovieItem

sealed class MainTabState {
    object Loading: MainTabState()
    data class Loaded(val movies: PagingData<MovieItem>) : MainTabState()

    object Refreshing: MainTabState()
    object RefreshError: MainTabState()

    object Empty: MainTabState()
    object Default: MainTabState()

    data class NavToDetailScreen(val id: String): MainTabState()
}
