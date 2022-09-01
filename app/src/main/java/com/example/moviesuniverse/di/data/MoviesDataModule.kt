package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesRemoteMediator
import com.example.moviesuniverse.data.MoviesRepositoryImpl
import com.example.moviesuniverse.data.local.MoviesLocalDataSource
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.RemoteKeysDao
import com.example.moviesuniverse.data.local.movies.RoomDataSourceImpl
import com.example.moviesuniverse.data.remote.MoviesRemoteDataSource
import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitDataSourceImpl
import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitService
import com.example.moviesuniverse.domain.MoviesRepository
import org.koin.dsl.module

val moviesDataModule = module {

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
        MoviesRepositoryImpl(localDataSource = get(), remoteDataSource = get())
    }

    single<MoviesRemoteDataSource> {
        MoviesRetrofitDataSourceImpl(retrofitService = get())
    }

    single<MoviesLocalDataSource> {
        RoomDataSourceImpl(moviesDao = get())
    }

    single<MoviesRemoteMediator> { (query: String) ->
        provideTop250MoviesRemoteMediator(
            service = get(),
            moviesDao = get(),
            remoteKeysDao = get(),
            query = query
        )
    }
}