package com.example.moviesuniverse.presentation.screens.tabs.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.usecases.LoadDetailMovieUseCase
import com.example.moviesuniverse.presentation.screens.EventHandler
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailEvent
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val loadDetailMovieUseCase: LoadDetailMovieUseCase) :
    ViewModel(), EventHandler<MovieDetailEvent> {

    private val _moveDetailState = MutableSharedFlow<MovieDetailState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val movieDetailState = _moveDetailState.asSharedFlow()

    override fun obtainEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.OnLoadMovie -> loadedMoveDetail(event.id)
        }
    }

    // TODO: will remove delay
    private fun loadedMoveDetail(id: String) {

        val movieDetailStateFlow = flow {
            loadDetailMovieUseCase(id)
                .onStart {
                    emit(MovieDetailState.Loading)
                    delay(1000)
                }
                .onEach { result ->
                    when (result) {
                        is ApiResult.Error -> emit(MovieDetailState.Error(result.error))
                        is ApiResult.Success -> emit(MovieDetailState.Success(result.movieDetailResponse))
                    }
                }
                .collect()
        }

        viewModelScope.launch {
            _moveDetailState.emitAll(movieDetailStateFlow)
        }
    }
}