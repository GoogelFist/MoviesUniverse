package com.example.moviesuniverse.presentation.screens.tabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesuniverse.domain.usecases.LoadMoviesUseCase
import com.example.moviesuniverse.presentation.screens.tabs.main.model.MainTabState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainTabViewModel(private val loadMoviesUseCase: LoadMoviesUseCase) : ViewModel() {

    private var _mainTabState = MutableStateFlow<MainTabState>(MainTabState.Loading)
    val mainTabState: StateFlow<MainTabState>
        get() = _mainTabState


    init {
        viewModelScope.launch {
            val list = loadMoviesUseCase.invoke()
            _mainTabState.value = MainTabState.Loaded(list)
        }
    }
}