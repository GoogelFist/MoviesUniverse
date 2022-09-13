package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.domain.usecases.LoadStaffDetailUseCase
import com.example.moviesuniverse.presentation.screens.EventHandler
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model.StaffDetailEvent
import com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model.StaffDetailState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class StaffDetailViewModel(private val loadStaffDetailUseCase: LoadStaffDetailUseCase) :
    ViewModel(), EventHandler<StaffDetailEvent> {

    private val _staffDetailState = MutableSharedFlow<StaffDetailState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val staffDetailState = _staffDetailState.asSharedFlow()

    override fun obtainEvent(event: StaffDetailEvent) {
        when (event) {
            is StaffDetailEvent.OnLoad -> loadedStaffDetail(event.id)
        }
    }

    private fun loadedStaffDetail(staffId: String) {

        val movieDetailStateFlow = flow {
            loadStaffDetailUseCase(staffId)
                .onStart {
                    emit(StaffDetailState.Loading)
                }
                .onEach { result ->
                    when (result) {
                        is ApiResult.Error -> emit(StaffDetailState.Error(result.error))
                        is ApiResult.Success -> emit(StaffDetailState.Success(result.data))
                    }
                }
                .collect()
        }

        viewModelScope.launch {
            _staffDetailState.emitAll(movieDetailStateFlow)
        }
    }
}