package com.example.moviesuniverse.presentation.screens.tabs.sraff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.usecases.LoadMovieStaffUseCase
import com.example.moviesuniverse.presentation.screens.EventHandler
import com.example.moviesuniverse.presentation.screens.tabs.sraff.model.MovieStaffEvent
import com.example.moviesuniverse.presentation.screens.tabs.sraff.model.MovieStaffState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieStaffViewModel(private val loadMovieStaffUseCase: LoadMovieStaffUseCase) : ViewModel(),
    EventHandler<MovieStaffEvent> {

    private val _moveStaffState = MutableSharedFlow<MovieStaffState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val moveStaffState = _moveStaffState.asSharedFlow()

    override fun obtainEvent(event: MovieStaffEvent) {
        when (event) {
            is MovieStaffEvent.OnLoadMovieStaff -> loadMovieStaff(event.movieId)
        }
    }

    private fun loadMovieStaff(movieId: String) {
        val movieStaffStateFlow = flow {
            loadMovieStaffUseCase(movieId)
                .onStart { emit(MovieStaffState.Loading) }
                .onEach { result ->
                    when (result) {
                        is ApiResult.Error -> emit(MovieStaffState.Error(result.error))
                        is ApiResult.Success -> emit(MovieStaffState.Success(result.data))
                    }
                }.collect()
        }

        viewModelScope.launch {
            _moveStaffState.emitAll(movieStaffStateFlow)
        }
    }
}