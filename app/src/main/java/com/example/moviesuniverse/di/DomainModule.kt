package com.example.moviesuniverse.di

import com.example.moviesuniverse.domain.usecases.LoadMoviesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<LoadMoviesUseCase> {
        LoadMoviesUseCase(moviesRepository = get())
    }
}