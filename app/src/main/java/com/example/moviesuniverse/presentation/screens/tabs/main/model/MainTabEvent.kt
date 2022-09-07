package com.example.moviesuniverse.presentation.screens.tabs.main.model

sealed class MainTabEvent {
    object OnEmpty: MainTabEvent()
    object OnLoading: MainTabEvent()
    object OnRefresh: MainTabEvent()
    object OnDefault: MainTabEvent()
    object OnRefreshError: MainTabEvent()
    data class OnNavToDetailScreen(val id: String): MainTabEvent()
}