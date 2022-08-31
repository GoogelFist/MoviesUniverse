package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesRepositoryImpl
import com.example.moviesuniverse.data.MoviesRemoteMediator
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.RemoteKeysDao
import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitService
import com.example.moviesuniverse.domain.MoviesRepository
import org.koin.dsl.module

const val TYPE_TOP_250 = "TOP_250_BEST_FILMS"

val dataModule = module {

    fun provideTop250MoviesRemoteMediator(
        service: MoviesRetrofitService,
        moviesDao: MoviesDao,
        remoteKeysDao: RemoteKeysDao,
        query: String
    ): MoviesRemoteMediator {
        return MoviesRemoteMediator(
            moviesRetrofitService = service,
            moviesDao = moviesDao,
            remoteKeysDao = remoteKeysDao,
            query = query
        )
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(moviesRemoteMediator = get(), moviesDao = get())
    }

    single<MoviesRemoteMediator> {
        provideTop250MoviesRemoteMediator(
            service = get(),
            moviesDao = get(),
            remoteKeysDao = get(),
            query = TYPE_TOP_250
        )
    }
}