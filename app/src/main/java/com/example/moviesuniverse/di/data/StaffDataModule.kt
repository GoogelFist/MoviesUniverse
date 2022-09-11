package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesStaffRepositoryImpl
import com.example.moviesuniverse.data.remote.MoviesStaffRemoteDataSource
import com.example.moviesuniverse.data.remote.staff.MoviesStaffRetrofitDataSourceImpl
import com.example.moviesuniverse.domain.MoviesStaffRepository
import org.koin.dsl.module

val staffDataModule = module {

    single<MoviesStaffRemoteDataSource> {
        MoviesStaffRetrofitDataSourceImpl(retrofitService = get())
    }

    single<MoviesStaffRepository> {
        MoviesStaffRepositoryImpl(moviesStaffRemoteDataSource = get())
    }
}