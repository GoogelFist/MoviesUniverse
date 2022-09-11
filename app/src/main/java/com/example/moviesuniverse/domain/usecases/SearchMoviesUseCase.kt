package com.example.moviesuniverse.domain.usecases

import androidx.paging.PagingData
import com.example.moviesuniverse.data.local.movies.models.MovieEntity
import com.example.moviesuniverse.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase (private val moviesRepository: MoviesRepository) {
    operator fun invoke(query: String): Flow<PagingData<MovieEntity>> {
       return moviesRepository.searchMovies(query)
    }
}