package com.example.moviesuniverse.di

import com.example.moviesuniverse.presentation.MainViewModel
import org.koin.dsl.module

val applicationModule = module {

    single<MainViewModel> {
        MainViewModel()
    }
}