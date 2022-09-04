package com.example.moviesuniverse.presentation.screens.tabs.detail

import androidx.lifecycle.ViewModel
import com.example.moviesuniverse.data.model.ApiResult
import com.example.moviesuniverse.domain.usecases.LoadDetailMovieUseCase
import com.example.moviesuniverse.presentation.screens.EventHandler
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailEvent
import com.example.moviesuniverse.presentation.screens.tabs.detail.model.MovieDetailState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class MovieDetailViewModel(private val loadDetailMovieUseCase: LoadDetailMovieUseCase) :
    ViewModel(), EventHandler<MovieDetailEvent> {

    private val _event = MutableSharedFlow<MovieDetailEvent>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieDetailState = _event.flatMapLatest { event ->
        when (event) {
            is MovieDetailEvent.OnLoadMovie -> {
                getMovieDetailFlow(event.id)
            }
        }
    }

    override fun obtainEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.OnLoadMovie -> _event.tryEmit(MovieDetailEvent.OnLoadMovie(event.id))
        }
    }

    // TODO: will remove delay
    private fun getMovieDetailFlow(id: String) = flow {

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
            }.collect()
    }
}