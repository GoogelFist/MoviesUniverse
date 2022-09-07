package com.example.moviesuniverse.presentation.screens.tabs.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesuniverse.domain.usecases.LoadMoviesUseCase
import com.example.moviesuniverse.presentation.screens.EventHandler
import com.example.moviesuniverse.presentation.screens.tabs.main.model.MainTabEvent
import com.example.moviesuniverse.presentation.screens.tabs.main.model.MainTabState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainTabViewModel(private val loadMoviesUseCase: LoadMoviesUseCase) : ViewModel(),
    EventHandler<MainTabEvent> {

    private val _movieState = MutableSharedFlow<MainTabState>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val movieState = _movieState.asSharedFlow()

    init {
        getMovieList()
    }

    override fun obtainEvent(event: MainTabEvent) {
        when (event) {
            MainTabEvent.OnLoading -> _movieState.tryEmit(MainTabState.Loading)

            MainTabEvent.OnRefresh -> _movieState.tryEmit(MainTabState.Refreshing)
            MainTabEvent.OnRefreshError -> _movieState.tryEmit(MainTabState.RefreshError)

            MainTabEvent.OnEmpty -> _movieState.tryEmit(MainTabState.Empty)
            MainTabEvent.OnDefault -> _movieState.tryEmit(MainTabState.Default)

            is MainTabEvent.OnNavToDetailScreen -> navigateToDetailScreen(event.id)
        }
    }

    private fun navigateToDetailScreen(id: String) {
        _movieState.tryEmit(MainTabState.NavToDetailScreen(id))
    }

    private fun getMovieList() {

        viewModelScope.launch {
            loadMoviesUseCase()
                .map { pagingData ->
                    pagingData.map { movieEntity -> movieEntity.toMovieItem() }
                }.cachedIn(viewModelScope).collectLatest {
                    _movieState.emit(MainTabState.Loaded(it))
                }
        }
    }
}