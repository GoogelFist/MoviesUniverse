package com.example.moviesuniverse.domain.usecases

import androidx.paging.PagingData
import com.example.moviesuniverse.domain.MoviesRepository
import com.example.moviesuniverse.domain.models.MovieItem
import kotlinx.coroutines.flow.Flow

class LoadMoviesUseCase (private val moviesRepository: MoviesRepository) {
    operator fun invoke(): Flow<PagingData<MovieItem>> {
       return moviesRepository.getTop250Movies()
    }
}