package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesRepositoryImpl
import com.example.moviesuniverse.data.local.movies.DataBase
import com.example.moviesuniverse.data.MoviesRemoteMediator
import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitService
import com.example.moviesuniverse.domain.MoviesRepository
import org.koin.dsl.module

const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"

val dataModule = module {

    fun provideTop250MoviesRemoteMediator(service: MoviesRetrofitService, moviesDB: DataBase, query: String): MoviesRemoteMediator {
        return MoviesRemoteMediator(moviesRetrofitService = service, moviesDB = moviesDB, query = query)
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(moviesRemoteMediator = get(), moviesDao = get())
    }

    single<MoviesRemoteMediator> {
        provideTop250MoviesRemoteMediator(service = get(), moviesDB = get(), query = TYPE_TOP_250)
    }
}