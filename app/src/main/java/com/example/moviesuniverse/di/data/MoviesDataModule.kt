package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.MoviesRepositoryImpl
import com.example.moviesuniverse.data.paging.MoviesSearchRemoteMediator
import com.example.moviesuniverse.data.paging.MoviesTop250RemoteMediator
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

    fun provideMoviesRemoteMediator(
        service: MoviesRetrofitService,
        moviesDao: MoviesDao,
        remoteKeysDao: RemoteKeysDao,
        query: String
    ): MoviesTop250RemoteMediator {
        return MoviesTop250RemoteMediator(
            moviesRetrofitService = service,
            moviesDao = moviesDao,
            remoteKeysDao = remoteKeysDao,
            query = query
        )
    }

    fun provideMoviesSearchRemoteMediator(
        service: MoviesRetrofitService,
        moviesDao: MoviesDao,
        remoteKeysDao: RemoteKeysDao,
        query: String
    ): MoviesSearchRemoteMediator {
        return MoviesSearchRemoteMediator(
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
        RoomDataSourceImpl(moviesDao = get(), moveDetailDao = get())
    }

    single<MoviesTop250RemoteMediator> { (query: String) ->
        provideMoviesRemoteMediator(
            service = get(),
            moviesDao = get(),
            remoteKeysDao = get(),
            query = query
        )
    }

    factory<MoviesSearchRemoteMediator> { (query: String) ->
        provideMoviesSearchRemoteMediator(
            service = get(),
            moviesDao = get(),
            remoteKeysDao = get(),
            query = query
        )
    }
}