package com.example.moviesuniverse.presentation.screens.tabs.sraff.detail.model

sealed class StaffDetailEvent {
    data class OnLoad(val id: String): StaffDetailEvent()
}