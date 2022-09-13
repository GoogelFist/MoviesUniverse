package com.example.moviesuniverse.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var _mainState = MutableStateFlow<MainState>(MainState.Loading)
    val mainState: StateFlow<MainState>
        get() = _mainState

    init {
        viewModelScope.launch {
            _mainState.value = MainState.Loading
            delay(SPLASH_SCREEN_DELAY)
            _mainState.value = MainState.Loaded
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DELAY = 800L
    }
}