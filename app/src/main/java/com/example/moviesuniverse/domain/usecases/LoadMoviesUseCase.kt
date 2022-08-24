package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.domain.MoviesRepository

class LoadMoviesUseCase (private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke() {
        moviesRepository.loadMovies()
    }
}