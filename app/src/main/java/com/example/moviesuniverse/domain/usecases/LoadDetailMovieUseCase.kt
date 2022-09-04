package com.example.moviesuniverse.domain.usecases

import com.example.moviesuniverse.data.model.ApiResult
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieDetail
import kotlinx.coroutines.flow.Flow

class LoadDetailMovieUseCase (private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(id: String): Flow<ApiResult<MovieDetail>> {
       return moviesRepository.getDetailMovie(id)
    }
}