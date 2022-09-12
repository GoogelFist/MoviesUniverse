package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesStaffRepositoryImpl
import com.example.moviesuniverse.data.local.MovieStaffLocalDataSource
import com.example.moviesuniverse.data.local.staff.MovieStaffRoomDataSourceImpl
import com.example.moviesuniverse.data.remote.MovieStaffRemoteDataSource
import com.example.moviesuniverse.data.remote.staff.MovieStaffRetrofitDataSourceImpl
import com.example.moviesuniverse.domain.MoviesStaffRepository
import org.koin.dsl.module

val staffDataModule = module {

    single<MovieStaffRemoteDataSource> {
        MovieStaffRetrofitDataSourceImpl(retrofitService = get())
    }

    single<MoviesStaffRepository> {
        MoviesStaffRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    single<MovieStaffLocalDataSource> {
        MovieStaffRoomDataSourceImpl(movieStaffDao = get())
    }
}