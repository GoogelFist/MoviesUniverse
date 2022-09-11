package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesStaffRepositoryImpl
import com.example.moviesuniverse.domain.MoviesStaffRepository
import org.koin.dsl.module

val staffDataModule = module {

    single<MoviesStaffRepository> {
        MoviesStaffRepositoryImpl()
    }
}