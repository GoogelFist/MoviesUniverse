package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem

class LoadMoviesUseCase (private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(): List<MovieItem> {
       return moviesRepository.loadMovies()
    }
}