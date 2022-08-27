package com.example.moviesuniverse.data.local.movies

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesuniverse.data.local.movies.models.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
}

private const val DB_VERSION = 1