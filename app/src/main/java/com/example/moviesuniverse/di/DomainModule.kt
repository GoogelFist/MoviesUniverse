package com.example.moviesuniverse.di

import com.example.moviesuniverse.domain.usecases.*
import org.koin.dsl.module

val domainModule = module {
    factory<LoadMoviesUseCase> {
        LoadMoviesUseCase(moviesRepository = get())
    }

    factory<LoadMovieDetailUseCase> {
        LoadMovieDetailUseCase(moviesRepository = get())
    }

    factory<SearchMoviesUseCase> {
        SearchMoviesUseCase(moviesRepository = get())
    }

    factory<LoadMovieStaffUseCase> {
        LoadMovieStaffUseCase(moviesStaffRepository = get())
    }

    factory<LoadMovieStaffDetailUseCase> {
        LoadMovieStaffDetailUseCase(moviesStaffRepository = get())
    }
}