package com.example.moviesuniverse.domain.usecases

import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow

class LoadMoviesUseCase (private val moviesRepository: MoviesRepository) {
    operator fun invoke(): Flow<PagingData<MovieEntity>> {
       return moviesRepository.getTop250Movies()
    }
}