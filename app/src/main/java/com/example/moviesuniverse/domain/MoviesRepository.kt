package com.example.moviesuniverse.domain

interface MoviesRepository {
    suspend fun loadMovies()
}