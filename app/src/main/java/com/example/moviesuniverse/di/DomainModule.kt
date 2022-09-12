package com.example.moviesuniverse.di

import com.example.moviesuniverse.domain.usecases.LoadDetailMovieUseCase
import com.example.moviesuniverse.domain.usecases.LoadMovieStaffUseCase
import com.example.moviesuniverse.domain.usecases.LoadMoviesUseCase
import com.example.moviesuniverse.domain.usecases.SearchMoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<LoadMoviesUseCase> {
        LoadMoviesUseCase(moviesRepository = get())
    }

    factory<LoadDetailMovieUseCase> {
        LoadDetailMovieUseCase(moviesRepository = get())
    }

    factory<SearchMoviesUseCase> {
        SearchMoviesUseCase(moviesRepository = get())
    }

    factory<LoadMovieStaffUseCase> {
        LoadMovieStaffUseCase(moviesStaffRepository = get())
    }
}