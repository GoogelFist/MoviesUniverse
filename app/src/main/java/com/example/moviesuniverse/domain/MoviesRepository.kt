package com.example.moviesuniverse.domain

import com.example.moviesuniverse.domain.models.MovieItem

interface MoviesRepository {
    suspend fun loadMovies(): List<MovieItem>
}