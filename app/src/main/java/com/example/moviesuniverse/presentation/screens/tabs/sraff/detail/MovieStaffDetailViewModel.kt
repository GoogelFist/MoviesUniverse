package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.usecases.LoadMovieStaffDetailUseCase
import com.example.moviesuniverse.presentation.screens.EventHandler
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model.MovieStaffDetailEvent
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model.MovieStaffDetailState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieStaffDetailViewModel(private val loadMovieStaffDetailUseCase: LoadMovieStaffDetailUseCase) :
    ViewModel(), EventHandler<MovieStaffDetailEvent> {

    private val _moveStaffDetailState = MutableSharedFlow<MovieStaffDetailState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val movieStaffDetailState = _moveStaffDetailState.asSharedFlow()

    override fun obtainEvent(event: MovieStaffDetailEvent) {
        when (event) {
            is MovieStaffDetailEvent.OnLoadMovieStaff -> loadedMoveDetail(event.id)
        }
    }

    private fun loadedMoveDetail(staffId: String) {

        val movieDetailStateFlow = flow {
            loadMovieStaffDetailUseCase(staffId)
                .onStart {
                    emit(MovieStaffDetailState.Loading)
                }
                .onEach { result ->
                    when (result) {
                        is ApiResult.Error -> emit(MovieStaffDetailState.Error(result.error))
                        is ApiResult.Success -> emit(MovieStaffDetailState.Success(result.data))
                    }
                }
                .collect()
        }

        viewModelScope.launch {
            _moveStaffDetailState.emitAll(movieDetailStateFlow)
        }
    }
}