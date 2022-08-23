package com.example.moviesuniverse.presentation

sealed class MainState {
    object Loading : MainState()
    object Loaded : MainState()
}