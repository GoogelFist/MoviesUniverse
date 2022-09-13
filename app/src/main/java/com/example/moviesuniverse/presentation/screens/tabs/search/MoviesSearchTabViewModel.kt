package com.example.moviesuniverse.presentation.screens.tabs.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviesuniverse.domain.models.MovieItem
import com.example.moviesuniverse.domain.usecases.SearchMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class MoviesSearchTabViewModel(private val searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {

    private val _query = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val query: SharedFlow<String> = _query.asSharedFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val movies: StateFlow<PagingData<MovieItem>> = query.flatMapLatest { query ->
        searchMovies(query)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun searchMovies(query: String): Flow<PagingData<MovieItem>> {
        return searchMoviesUseCase(query)
            .map { pagingData -> pagingData.map { movieEntity -> movieEntity.toMovieItem() } }
            .cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        _query.tryEmit(query)
    }
}