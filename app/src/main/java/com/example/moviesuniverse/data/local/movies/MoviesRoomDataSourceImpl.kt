package com.example.moviesuniverse.data.local.movies

import com.example.moviesuniverse.data.local.MoviesLocalDataSource
import com.example.moviesuniverse.data.local.movies.models.MovieEntity

class MoviesRoomDataSourceImpl(private val moviesDao: MoviesDao): MoviesLocalDataSource {

    override suspend fun saveMovies(movieEntities: List<MovieEntity>) {
        moviesDao.saveMovies(movieEntities)
    }
}