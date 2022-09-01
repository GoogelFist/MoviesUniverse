package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieDetail

class LoadDetailMovieUseCase (private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(id: String): MovieDetail {
       return moviesRepository.getDetailMovie(id)
    }
}