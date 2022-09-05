package com.example.moviesuniverse.data.local.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesuniverse.data.local.movies.models.MovieDetailEntity
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.data.local.movies.models.RemoteKeyEntity

@Database(
    entities = [MovieEntity::class, RemoteKeyEntity::class, MovieDetailEntity::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
    abstract fun getMovieDetailDao(): MoviesDetailDao
    abstract fun getRemoteKeyDao(): RemoteKeysDao
}

private const val DB_VERSION = 1