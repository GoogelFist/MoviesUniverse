package com.example.moviesuniverse.di

import com.example.moviesuniverse.presentation.MainViewModel
import com.example.moviesuniverse.presentation.screens.tabs.detail.MovieDetailViewModel
import com.example.moviesuniverse.presentation.screens.tabs.main.MainTabViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val applicationModule = module {

    viewModel<MainViewModel> {
        MainViewModel()
    }

    viewModel<MainTabViewModel> {
        MainTabViewModel(loadMoviesUseCase = get())
    }

    viewModel<MovieDetailViewModel> {
        MovieDetailViewModel(loadDetailMovieUseCase = get())
    }
}