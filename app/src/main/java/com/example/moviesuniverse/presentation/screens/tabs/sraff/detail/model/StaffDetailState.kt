package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model

import com.example.moviesuniverse.domain.models.StaffDetail


sealed class StaffDetailState {
    object Loading : StaffDetailState()
    data class Success(val staffDetail: StaffDetail) : StaffDetailState()
    data class Error(val exception: Throwable) : StaffDetailState()
}