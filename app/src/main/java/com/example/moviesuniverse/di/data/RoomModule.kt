package com.example.moviesuniverse.di.data

import android.app.Application
import androidx.room.Room
import com.example.moviesuniverse.data.local.movies.DataBase
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.MoviesDetailDao
import com.example.moviesuniverse.data.local.movies.RemoteKeysDao
import org.koin.dsl.module

private const val DATABASE_NAME = "Movie data base"

val roomModule = module {

    fun provideDatabase(context: Application): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
            .build()
    }

    fun provideMovieDao(database: DataBase): MoviesDao {
        return database.getMovieDao()
    }

    fun provideMovieDetailDao(database: DataBase): MoviesDetailDao {
        return database.getMovieDetailDao()
    }

    fun provideRemoteKeysDao(database: DataBase): RemoteKeysDao {
        return database.getRemoteKeyDao()
    }

    single<DataBase> { provideDatabase(context = get()) }
    single<MoviesDao> { provideMovieDao(database = get()) }
    single<MoviesDetailDao> { provideMovieDetailDao(database = get()) }
    single<RemoteKeysDao> { provideRemoteKeysDao(database = get()) }
}