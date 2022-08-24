package com.example.moviesuniverse.di

import com.example.moviesuniverse.presentation.MainViewModel
import com.example.moviesuniverse.presentation.screens.tabs.main.MainTabViewModel
import org.koin.dsl.module

val applicationModule = module {

    single<MainViewModel> {
        MainViewModel()
    }

    single<MainTabViewModel> {
        MainTabViewModel(loadMoviesUseCase = get())
    }
}