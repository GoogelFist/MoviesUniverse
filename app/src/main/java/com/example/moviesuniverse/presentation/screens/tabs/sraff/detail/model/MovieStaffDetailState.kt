package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model

import com.example.moviesuniverse.domain.models.StaffDetail


sealed class MovieStaffDetailState {
    object Loading : MovieStaffDetailState()
    data class Success(val staffDetail: StaffDetail) : MovieStaffDetailState()
    data class Error(val exception: Throwable) : MovieStaffDetailState()
}