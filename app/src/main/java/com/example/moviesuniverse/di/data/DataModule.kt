package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesRepositoryImpl
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.data.remote.movies.MoviePagingSource
import com.example.moviesuniverse.data.remote.movies.MoviesRemoteDataSourceImpl
import com.example.moviesuniverse.domain.MoviesRepository
import org.koin.dsl.module

val dataModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(moviesRemoteDataSource = get(), moviePagingSource = get())
    }

    single<MoviesRemoteDataSource> {
        MoviesRemoteDataSourceImpl(moviesRetrofitService = get())
    }

    single<MoviePagingSource> {
        MoviePagingSource(moviesRetrofitService = get())
    }
}