package com.example.moviesuniverse.presentation.screens.tabs.main.model

sealed class MainTabEvent {
    object OnLoading: MainTabEvent()

    object OnRefresh: MainTabEvent()
    object OnRefreshError: MainTabEvent()

    object OnEmpty: MainTabEvent()
    object OnDefault: MainTabEvent()

    data class OnNavToDetailScreen(val id: String): MainTabEvent()
}