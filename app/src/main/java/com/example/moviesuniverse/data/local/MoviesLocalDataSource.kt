package com.example.moviesuniverse.data.local

import com.example.moviesuniverse.data.local.movies.models.MovieEntity

interface MoviesLocalDataSource {

    suspend fun saveMovies(movieEntities: List<MovieEntity>)
}